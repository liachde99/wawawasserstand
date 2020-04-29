package de.wasserwacht.wasserstand;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		mv.addObject("timestamp", time);
		
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
			
			this.stand = stand;
			this.time = new SimpleDateFormat("dd:MM - hh:mm").format(new Date());
		}
		return "";
	}
}
