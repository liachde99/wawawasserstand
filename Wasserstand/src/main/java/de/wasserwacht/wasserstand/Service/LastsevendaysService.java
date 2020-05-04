package de.wasserwacht.wasserstand.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;
import de.wasserwacht.wasserstand.Repository.LastsevendaysRepo;

@Service
public class LastsevendaysService {
	
	private LastsevendaysRepo repo;
	
	public LastsevendaysService(LastsevendaysRepo repo) {
		this.repo = repo;
	}
	
	public Optional<Lastsevendays> findById(Long id){
		return repo.findById(id);
	}
	
	public List<Lastsevendays> findByTageswasserstand_id(Long tageswasserstand_id){
		return repo.findByTageswasserstand_id(tageswasserstand_id);
	}
	
	public <S extends Lastsevendays> S save(S lastsevendays) {
		return repo.save(lastsevendays);
	}
	
	public void truncate() {
		repo.truncateLastsevendays();
	}

}
