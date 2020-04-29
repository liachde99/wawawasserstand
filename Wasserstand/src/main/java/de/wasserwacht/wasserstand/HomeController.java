package de.wasserwacht.wasserstand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Service.WasserstandService;


@Controller
public class HomeController {
	
	private int stand;
	private String time = "";
	
	@Autowired
	private WasserstandService service;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		mv.addObject("wasserstand", this.stand);
		mv.addObject("timestamp", this.time);
		
		return mv;
	}
	
	@GetMapping("/postgresql")
	public ModelAndView postgresql() {
		ModelAndView mv = new ModelAndView("index.html");
		
		Wasserstand last = service.findTopByOrderByIdDesc();
		
		mv.addObject("wasserstand", last.getWasserstand());
		mv.addObject("timestamp", last.stamp());
		
		return mv;
	}
	
	
	@GetMapping("/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			
			SimpleDateFormat format = new SimpleDateFormat("dd.MM - HH:mm", Locale.GERMANY);
			Date datum = new Date();
			this.time = format.format(datum);
			
			this.stand = stand;
			
			
		}
		return "";
	}
	
	@GetMapping("/{passwort}/{stand}/time")
	public String sendwasserstandtime(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			
			SimpleDateFormat format = new SimpleDateFormat("dd.MM - HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("CET"));
			Date datum = new Date();
			this.time = format.format(datum);
			
			this.stand = stand;
			
			
		}
		return time;
	}
}
