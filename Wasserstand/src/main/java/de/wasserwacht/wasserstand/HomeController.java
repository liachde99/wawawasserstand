package de.wasserwacht.wasserstand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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


@Controller
public class HomeController {
	
	@Autowired
	private WasserstandService service;
	
	@Autowired
	private LastsevendaysService lastsevendaysService;
	
	@Autowired
	private TagesdurchschnittService tagesdurchschnittService;
	
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
	
	@GetMapping("/impressum")
	public ModelAndView impressum() {
		return new ModelAndView("impressum.html");
	}
	
	@GetMapping("/datenschutz")
	public ModelAndView datenschutz() {
		return new ModelAndView("datenschutz.html");
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
	
	@GetMapping("/month")
	@ResponseBody
	public List<Tagesdurchschnitt> getchartmonth(){
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		List<Tagesdurchschnitt> td =  tagesdurchschnittService.findByMonthAndYear(date.getMonthValue(), date.getYear());
		return td;
	}
	
	@GetMapping("/td/{day}/{month}/{year}/{week}")
	public void td(@PathVariable("day") int day, @PathVariable("month") int month,@PathVariable("year") int year, @PathVariable("week") int week) {
		int durchschnitt = 0;
		double tempdurchschnitt = 0;
		int counter = 0;
		
		List<Wasserstand> staende = service.findByDayAndMonthAndYear(day, month, year);
		
		if(staende.size()!=0) {
			for (Wasserstand wasserstand : staende) {
				durchschnitt += wasserstand.getWasserstand();
				tempdurchschnitt += wasserstand.getTemperatur();
				counter++;
			}
			
			if(tagesdurchschnittService.findByDayAndMonthAndYear(day, month, year)==null) {
				tagesdurchschnittService.save(new Tagesdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),day,month,year,week));
			}
		}
	}
	
	@GetMapping("/lsd")
	public void lsd() {
		lastsevendaysService.truncate();
		for(int i=7;i>=1;i--) {
			LocalDateTime date =  LocalDateTime.now(ZoneId.of("CET")).minus(i, ChronoUnit.DAYS);
			Tagesdurchschnitt td = tagesdurchschnittService.findByDayAndMonthAndYear(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
			if(td!=null) {
				lastsevendaysService.save(new Lastsevendays(td.getId()));
			}
		}
	}
}
