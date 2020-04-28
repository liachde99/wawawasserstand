package de.wasserwacht.wasserstand;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import de.wasserwacht.wasserstand.entities.Wasserstand;
import de.wasserwacht.wasserstand.service.WasserstandService;

@Controller
public class HomeController {
	
	@Autowired
	private WasserstandService wasserstandService;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		String date;
		int stand;
		Wasserstand last = wasserstandService.findLast();
		
		if(last!=null) {
			date = last.getDay() + '.' + last.getMonth() + ' ' + last.getHour() + ':' + last.getMin();
			stand = last.getStand();
		}else {
			date = "none";
			stand = 0;
		}
		
		mv.addObject("lasttimestamp", date);
		mv.addObject("wasserstand", stand);
		
		mv.addObject("tag", wasserstandService.findByDay(new SimpleDateFormat("dd").format(new Date())));
		mv.addObject("monat", wasserstandService.findByMonth(new SimpleDateFormat("MM").format(new Date())));
		mv.addObject("jahr", wasserstandService.findByYear(new SimpleDateFormat("YYYY").format(new Date())));
		return mv;
	}
	
	
	
	@GetMapping("/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			wasserstandService.save(new Wasserstand(stand));
		}
		return "";
	}
}
