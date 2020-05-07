package de.wasserwacht.wasserstand.schedule;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;
import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;
import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Service.LastsevendaysService;
import de.wasserwacht.wasserstand.Service.MonatsdurchschnittService;
import de.wasserwacht.wasserstand.Service.TagesdurchschnittService;
import de.wasserwacht.wasserstand.Service.WasserstandService;

@Component
public class ScheduledTasks {
	
	List<Wasserstand> staende = new ArrayList<>();
	List<Tagesdurchschnitt> tagesdurchschnitte = new ArrayList<>();
	LocalDateTime date;
	
	@Autowired
	private WasserstandService service;
	
	@Autowired
	private TagesdurchschnittService tdservice;
	
	@Autowired
	private MonatsdurchschnittService mdservice;
	
	@Autowired
	private LastsevendaysService lsdService;
	
	
	
	@Scheduled(cron="0 55 8 * * ?")
	public void daily() {
		System.out.println("schedule");
		tagesdurchschnitt();
		lastsevendays();
	}
	
	@Scheduled(cron="0 0 0 1 * ?")
	public void monthly() {
		monatsdurchschnitt();
	}
	
	public void lastsevendays() {
		lsdService.truncate();
		for(int i=7;i>=1;i--) {
			date =  LocalDateTime.now(ZoneId.of("CET")).minus(i, ChronoUnit.DAYS);
			Tagesdurchschnitt td = tdservice.findByDayAndMonthAndYear(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
			if(td!=null) {
				lsdService.save(new Lastsevendays(td.getId()));
			}
		}
	}
	public void tagesdurchschnitt() {
		int durchschnitt = 0;
		double tempdurchschnitt = 0;
		int counter = 0;
		
		date =  LocalDateTime.now(ZoneId.of("CET")).minus(1, ChronoUnit.DAYS);
		staende = service.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		
		if(staende.size()!=0) {
			for (Wasserstand wasserstand : staende) {
				durchschnitt += wasserstand.getWasserstand();
				tempdurchschnitt += wasserstand.getTemperatur();
				counter++;
			}
			
			tdservice.save(new Tagesdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),date.getDayOfMonth(),date.getMonthValue(),date.getYear(),date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)));
		}
	}
	public void monatsdurchschnitt() {
		int durchschnitt = 0;
		double tempdurchschnitt = 0;
		int counter = 0;
		date =  LocalDateTime.now(ZoneId.of("CET")).minus(1, ChronoUnit.DAYS);
		tagesdurchschnitte = tdservice.findByMonthAndYear(date.getMonthValue(), date.getYear());
		
		if(tagesdurchschnitte.size()!=0) {
			for (Tagesdurchschnitt tagesdurchschnitt : tagesdurchschnitte) {
				durchschnitt += tagesdurchschnitt.getWasserstand();
				tempdurchschnitt  += tagesdurchschnitt.getTemperatur();
				counter++;
			}
			
			mdservice.save(new Monatsdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),date.getMonthValue(),date.getYear()));
		}
	}
}
