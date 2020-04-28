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
	
	private String time;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		Wasserstand last = wasserstandService.findLast();
		String date = last.getDay() + '.' + last.getMonth() + ' ' + last.getHour() + ':' + last.getMin();
		
		mv.addObject("lasttimestamp", date);
		mv.addObject("wasserstand", last);
		return mv;
	}
	
	
	
	@GetMapping("/send/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String password,@PathVariable("stand") int stand) {
		if(password.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			wasserstandService.save(new Wasserstand(stand));
		}
		return "";
	}
}
