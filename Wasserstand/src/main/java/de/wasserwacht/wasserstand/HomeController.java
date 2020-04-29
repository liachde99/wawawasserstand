package de.wasserwacht.wasserstand;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
	
	
//	TEMP
	
	private int stand;
	private String time = "";
	
//	TEMPEND
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		mv.addObject("wasserstand", this.stand);
		mv.addObject("timestamp", time);
		
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
