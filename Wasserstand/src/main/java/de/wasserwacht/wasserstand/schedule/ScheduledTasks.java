package de.wasserwacht.wasserstand.schedule;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(fixedDelay = 3000)
	public void test() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		int durchschnitt = 0;
		double tempdurchschnitt = 0;
		int counter = 0;
		
		date =  LocalDateTime.now(ZoneId.of("CET")).minus(1, ChronoUnit.DAYS);
		staende = service.findByDayAndMonthAndYear(9, 5, 2020);
		
		if(staende.size()!=0) {
			for (Wasserstand wasserstand : staende) {
				durchschnitt += wasserstand.getWasserstand();
				tempdurchschnitt += wasserstand.getTemperatur();
				counter++;
			}
			
			if(tdservice.findByDayAndMonthAndYear(9, 5, 2020)==null) {
				tdservice.save(new Tagesdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),9,5,2020,19));
			}
		}
		
		staende = service.findByDayAndMonthAndYear(10, 5, 2020);
		
		if(staende.size()!=0) {
			for (Wasserstand wasserstand : staende) {
				durchschnitt += wasserstand.getWasserstand();
				tempdurchschnitt += wasserstand.getTemperatur();
				counter++;
			}
			
			if(tdservice.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear())==null) {
				tdservice.save(new Tagesdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),10,5,2020,19));
			}
		}
	}
	
	
	@Scheduled(cron = "0 0 2 * * ?")
	public void daily() {
		tagesdurchschnitt();
		lastsevendays();
	}
	
	@Scheduled(cron="0 0 2 1 * ?")
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
			
			if(tdservice.findByDayAndMonthAndYear(date.getDayOfMonth(), date.getMonthValue(), date.getYear())==null) {
				tdservice.save(new Tagesdurchschnitt((durchschnitt/counter),(tempdurchschnitt/counter),date.getDayOfMonth(),date.getMonthValue(),date.getYear(),date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)));
			}
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
