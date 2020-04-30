package de.wasserwacht.wasserstand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Repository.WasserstandRepo;


@Controller
public class HomeController {
	
	private int stand;
	private String time = "";
	
	@Autowired
	private WasserstandRepo repo;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		mv.addObject("wasserstand", this.stand);
		mv.addObject("timestamp", this.time);
		
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
	
	
	
	@GetMapping("/postgresql")
	public ModelAndView postgresql() {
		ModelAndView mv = new ModelAndView("index.html");
		
		Wasserstand last = repo.findTopByOrderByIdDesc();
		
		if(last!=null) {
			mv.addObject("wasserstand", last.getWasserstand());
		}else {
			mv.addObject("wasserstand", 0);
		}
		mv.addObject("timestamp", last.stamp());
		
		return mv;
	}
	
	@GetMapping("/{passwort}/{stand}/postgresql")
	public String sendwasserstandpostgresql(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			repo.save(new Wasserstand(stand));
		}
		return "";
	}
}
