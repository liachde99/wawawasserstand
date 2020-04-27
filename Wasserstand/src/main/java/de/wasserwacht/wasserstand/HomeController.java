package de.wasserwacht.wasserstand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
	
	private int wasserstand;
	
	@GetMapping("/")
	public String test() {
		return "index.php";
	}
	
	@GetMapping("/request")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index.html");
		
		mv.addObject("wasserstand", this.wasserstand);
		return mv;
	}
	
	@GetMapping("/send/{stand}")
	public RedirectView sendwasserstand(@PathVariable("stand") int stand) {
		this.wasserstand = stand;
		return new RedirectView("/request");
	}
}
