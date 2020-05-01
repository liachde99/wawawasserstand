package de.wasserwacht.wasserstand.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Repository.WasserstandRepo;

@Service
public class WasserstandService {
	
	private WasserstandRepo repo;
	
	public WasserstandService(WasserstandRepo repo) {
		this.repo = repo;
	}
	
	public Optional<Wasserstand> findById(Long id){
		return repo.findById(id);
	}
	
	public Wasserstand findTopByOrderByIdDesc() {
		return repo.findTopByOrderByIdDesc();
	}
	
	public List<Wasserstand> findByDayAndMonthAndYear(int day,int month,int year){
		return repo.findByDayAndMonthAndYear(day, month, year);
	}
	
	public List<Wasserstand> findByMonthAndYear(int month,int year){
		return tagesdurchschnitt(repo.findByMonthAndYear(month, year));
	}
	
	public List<Wasserstand> findByYear(int year){
		return monatsdurchschnitt(repo.findByYear(year));
	}
	
	public List<Wasserstand> findByWeek(int week){
		System.out.println("3");
		return tagesdurchschnitt(repo.findByWeek(week));
	}
	
	public <S extends Wasserstand> S save(S wasserstand) {
		return repo.save(wasserstand);
	}
	
	private List<Wasserstand> tagesdurchschnitt(List<Wasserstand> wasserstand){
		List<Wasserstand> wasserstandTagesdurchschnitt = new ArrayList<>();
		
		Wasserstand tempSpeicher = wasserstand.get(0);
		int gesamtStand = 0;
		int counter = 0;
		
		for(int i = 1; i < wasserstand.size(); i++) {
			
			Wasserstand w = wasserstand.get(i);
			
			if(tempSpeicher.getYear() == w.getYear()) {
				if(tempSpeicher.getMonth() == w.getMonth()) {
					if(tempSpeicher.getDay() == w.getDay()) {
						System.out.println(".");
						gesamtStand += w.getWasserstand();
						System.out.println(gesamtStand);
						counter++;
						continue;
					}
				}
			}
			
			int durchschnitt = gesamtStand / counter;
			wasserstandTagesdurchschnitt.add(new Wasserstand(	durchschnitt,
																tempSpeicher.getDay(),
																tempSpeicher.getMonth(),
																tempSpeicher.getYear(),
																0,
																0
																)
					);
			System.out.println(durchschnitt);
			tempSpeicher = w;
			counter = 0;
			gesamtStand = 0;
		}
		System.out.println("----------------");
		return wasserstandTagesdurchschnitt;
	}

	private List<Wasserstand> monatsdurchschnitt(List<Wasserstand> wasserstand){
		List<Wasserstand> wasserstandMonatsdurchschnitt = new ArrayList<>();
		
		Wasserstand tempSpeicher = wasserstand.get(0);
		int gesamtStand = 0;
		int counter = 0;
		
		for(int i = 1; i < wasserstand.size(); i++) {
			
			Wasserstand w = wasserstand.get(i);
			
			if(tempSpeicher.getYear() == w.getYear()) {
				if(tempSpeicher.getMonth() == w.getMonth()) {
					gesamtStand += w.getWasserstand();
					counter++;
					continue;
				}
			}
			
			wasserstandMonatsdurchschnitt.add(new Wasserstand(	(gesamtStand / counter),
																tempSpeicher.getDay(),
																tempSpeicher.getMonth(),
																tempSpeicher.getYear(),
																0,
																0
																)
					);
			tempSpeicher = w;
			counter = 0;
			gesamtStand = 0;
		}
		
		return wasserstandMonatsdurchschnitt;
	}
}
