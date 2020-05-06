package de.wasserwacht.wasserstand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;
import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Service.LastsevendaysService;
import de.wasserwacht.wasserstand.Service.TagesdurchschnittService;
import de.wasserwacht.wasserstand.Service.WasserstandService;
import de.wasserwacht.wasserstand.schedule.ScheduledTasks;


@Controller
public class HomeController {
	
	@Autowired
	private WasserstandService service;
	
	@Autowired
	private LastsevendaysService lastsevendaysService;
	
	@Autowired
	private TagesdurchschnittService tagesdurchschnittService;

	@Autowired
	private ScheduledTasks st;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		Wasserstand last = service.findTopByOrderByIdDesc();
		
		if(last!=null) {
			mv.addObject("wasserstand", last.getWasserstand());
			mv.addObject("temperatur", last.getTemperatur());
			mv.addObject("timestamp", last.stamp());
		}else {
			mv.addObject("wasserstand", 0);
			mv.addObject("temperatur", 0);
			mv.addObject("timestamp", "none");
		}
		
		return mv;
	}
	
	@GetMapping("/lastsevendays")
	@ResponseBody
	public List<Tagesdurchschnitt> getchartdatalastsevendays(){
		List<Lastsevendays> lsd = new ArrayList<>();
		List<Tagesdurchschnitt> wsl = new ArrayList<>();
		lsd = lastsevendaysService.findAll();
		for (Lastsevendays lastsevendays : lsd) {
			wsl.add(tagesdurchschnittService.findById(lastsevendays.getTagesWasserstanId()));
		}
		
		return wsl;
	}
	
	@GetMapping("/today")
	@ResponseBody
	public List<Wasserstand> getchartdatatoday(){
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		return service.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
	}
	
	@GetMapping("/{passwort}/{stand}/{temperatur}")
	public String sendwasserstandundtemperatur(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand,@PathVariable("temperatur") double temperatur) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			service.save(new Wasserstand(stand,(temperatur/10)));
		}
		return "";
	}
	
	@GetMapping("/st")
	public void lsd() {
		st.lastsevendays();
	}
}
