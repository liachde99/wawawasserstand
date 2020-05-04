package de.wasserwacht.wasserstand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.node.ObjectNode;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;
import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;
import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Service.LastsevendaysService;
import de.wasserwacht.wasserstand.Service.MonatsdurchschnittService;
import de.wasserwacht.wasserstand.Service.TagesdurchschnittService;
import de.wasserwacht.wasserstand.Service.WasserstandService;
import de.wasserwacht.wasserstand.schedule.ScheduledTasks;


@Controller
public class HomeController {
	
	@Autowired
	private WasserstandService service;
	
	@Autowired
	private ScheduledTasks task;
	
	
	
	@Autowired
	private TagesdurchschnittService tdservice;
	
	@Autowired
	private MonatsdurchschnittService mdservice;
	
	@Autowired
	private LastsevendaysService lsdService;
	
	
	
	
	
	
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		Wasserstand last = service.findTopByOrderByIdDesc();
		
		if(last!=null) {
			mv.addObject("wasserstand", last.getWasserstand());
		}else {
			mv.addObject("wasserstand", 0);
		}
		mv.addObject("timestamp", last.stamp());
		
		return mv;
	}
	
	@GetMapping("/week")
	public String week() {
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		return date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + "";
	}
	
	@PostMapping("/chart")
	@ResponseBody
	public List<Wasserstand> getchartdata(@RequestBody ObjectNode on){
		String searched = on.get("searched").asText();
		List<Wasserstand> wsl = new ArrayList<>();
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		wsl = service.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		
		return wsl;
	}
	
	@GetMapping("/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			service.save(new Wasserstand(stand));
		}
		return "";
	}
	
	@GetMapping("/force")
	public String forceing(){
		force();
		return "";
	}
	
	public void force() {
		List<Wasserstand> staende = new ArrayList<>();
		List<Tagesdurchschnitt> tagesdurchschnitte = new ArrayList<>();
		LocalDateTime date;

		int durchschnitt = 0;
		date =  LocalDateTime.now(ZoneId.of("CET"));
		do {
			staende = service.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
			
			if(!staende.isEmpty()) {
				for (Wasserstand wasserstand : staende) {
					durchschnitt += wasserstand.getWasserstand();
				}
				
				tdservice.save(new Tagesdurchschnitt(durchschnitt,date.getDayOfMonth(),date.getMonthValue(),date.getYear(),date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)));
			}
			date.minus(1, ChronoUnit.DAYS);
		}while(!staende.isEmpty());
		
		
		
		lsdService.truncate();
		for(int i=1;i<=7;i++) {
			date =  LocalDateTime.now(ZoneId.of("CET")).minus(i, ChronoUnit.DAYS);
			Tagesdurchschnitt td = tdservice.findByDayAndMonthAndYear(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
			if(td!=null) {
				lsdService.save(new Lastsevendays(td.getId()));
			}
		}
		
		durchschnitt = 0;
		date =  LocalDateTime.now(ZoneId.of("CET"));
		
		do {
			
			tagesdurchschnitte = tdservice.findByMonthAndYear(date.getMonthValue(), date.getYear());
			if(tagesdurchschnitte.size()!=0) {
				for (Tagesdurchschnitt tagesdurchschnitt : tagesdurchschnitte) {
					durchschnitt += tagesdurchschnitt.getWasserstand();
				}
				
				mdservice.save(new Monatsdurchschnitt(durchschnitt,date.getMonthValue(),date.getYear()));
			}
			date.minus(1, ChronoUnit.DAYS);
		}while(!tagesdurchschnitte.isEmpty());
	}
}
