package de.wasserwacht.wasserstand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	private int wasserstand;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		mv.addObject("wasserstand", this.wasserstand);
		return mv;
	}
	
	@GetMapping("/send/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String password,@PathVariable("stand") int stand) {
		if(password.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			this.wasserstand = stand;
		}
		return "";
	}
}
