package de.wasserwacht.wasserstand.Service;

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
	
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	
	public <S extends Wasserstand> S save(S wasserstand) {
		return repo.save(wasserstand);
	}

}
