package de.wasserwacht.wasserstand;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	private double temperatur;
	
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
		}else {
			mv.addObject("wasserstand", 0);
		}
		mv.addObject("timestamp", last.stamp());
		mv.addObject("temperatur", this.temperatur);
		
		return mv;
	}
	
	@GetMapping("/tagesdurchschnitt")
	@ResponseBody
	public List<Tagesdurchschnitt> getchartdata(){
		List<Lastsevendays> lsd = new ArrayList<>();
		List<Tagesdurchschnitt> wsl = new ArrayList<>();
		lsd = lastsevendaysService.findAll();
		for (Lastsevendays lastsevendays : lsd) {
			wsl.add(tagesdurchschnittService.findById(lastsevendays.getTagesWasserstanId()));
		}
		
		return wsl;
	}
	
	@GetMapping("/{passwort}/{stand}/{temperatur}")
	public String sendwasserstandundtemperatur(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand,@PathVariable("temperatur") int temperatur) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			service.save(new Wasserstand(stand));
			this.temperatur = temperatur / 10;
		}
		return "";
	}
}
