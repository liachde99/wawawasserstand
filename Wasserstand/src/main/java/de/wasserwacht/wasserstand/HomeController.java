package de.wasserwacht.wasserstand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.node.ObjectNode;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Service.WasserstandService;


@Controller
public class HomeController {
	
	@Autowired
	private WasserstandService service;
	
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
		
		return mv;
	}
	
	@GetMapping("/week")
	public String week() {
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		return date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + "";
	}
	
	@PostMapping("/chart")
	@ResponseBody
	public List<Wasserstand> getchartdata(@RequestBody ObjectNode on){
		String searched = on.get("searched").asText();
		List<Wasserstand> wsl = new ArrayList<>();
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		System.out.println("1");
		
		switch(searched) {
		case "week":
			System.out.println("2");
			wsl = service.findByWeek(date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
			System.out.println(wsl.size());
			break;
		case "year":
			wsl = service.findByYear(date.getYear());
			break;
		case "month":
			wsl = service.findByMonthAndYear(date.getMonthValue(), date.getYear());
			break;
		default:
			wsl = service.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		}
		
		return wsl;
	}
	
	@GetMapping("/{passwort}/{stand}")
	public String sendwasserstand(@PathVariable("passwort") String passwort,@PathVariable("stand") int stand) {
		if(passwort.equalsIgnoreCase("gxcxWUxezdAgrhZz2EZH")) {
			service.save(new Wasserstand(stand));
		}
		return "";
	}
}
