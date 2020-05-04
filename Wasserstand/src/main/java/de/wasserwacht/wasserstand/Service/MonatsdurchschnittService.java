package de.wasserwacht.wasserstand.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;
import de.wasserwacht.wasserstand.Repository.MonatsdurchschnittRepo;

@Service
public class MonatsdurchschnittService {
	
	private MonatsdurchschnittRepo repo;
	
	public MonatsdurchschnittService(MonatsdurchschnittRepo repo) {
		this.repo = repo;
	}
	
	public Optional<Monatsdurchschnitt> findById(Long id){
		return repo.findById(id);
	}
	
	public List<Monatsdurchschnitt> findByMonthAndYear(int year){
		return repo.findByYear(year);
	}
	
	public <S extends Monatsdurchschnitt> S save(S monatsdurchschnitt) {
		return repo.save(monatsdurchschnitt);
	}

}
