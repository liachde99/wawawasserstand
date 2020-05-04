package de.wasserwacht.wasserstand.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;
import de.wasserwacht.wasserstand.Repository.TagesdurchschnittRepo;

@Service
public class TagesdurchschnittService {
	
	private TagesdurchschnittRepo repo;
	
	public TagesdurchschnittService(TagesdurchschnittRepo repo) {
		this.repo = repo;
	}
	
	public Optional<Tagesdurchschnitt> findById(Long id){
		return repo.findById(id);
	}
	
	public List<Tagesdurchschnitt> findByMonthAndYear(int month,int year){
		return repo.findByMonthAndYear(month, year);
	}
	
	public List<Tagesdurchschnitt> findByWeek(int week){
		return repo.findByWeek(week);
	}
	
	public <S extends Tagesdurchschnitt> S save(S tagesdurchschnitt) {
		return repo.save(tagesdurchschnitt);
	}

}
