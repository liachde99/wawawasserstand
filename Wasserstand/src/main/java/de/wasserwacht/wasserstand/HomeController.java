package de.wasserwacht.wasserstand;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Repository.WasserstandRepo;


@Controller
public class HomeController {
	
	@Autowired
	private WasserstandRepo repo;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		Wasserstand last = repo.findTopByOrderByIdDesc();
		
		if(last!=null) {
			mv.addObject("wasserstand", last.getWasserstand());
		}else {
			mv.addObject("wasserstand", 0);
		}
		mv.addObject("timestamp", last.stamp());
		mv.addObject("data", repo.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear()));
		
		return mv;
	}
	
	@GetMapping("/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			repo.save(new Wasserstand(stand));
		}
		return "";
	}
}
