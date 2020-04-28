package de.wasserwacht.wasserstand.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.entities.Wasserstand;
import de.wasserwacht.wasserstand.repository.WasserstandRepo;

@Service
public class WasserstandService {
	
	private WasserstandRepo repo;
	
	public WasserstandService(WasserstandRepo repo) {
		this.repo = repo;
	}
	
	public Wasserstand findById(Long id) {
		return repo.findById(id);
	}
	
	public Wasserstand findLast() {
		return repo.findTopByOrderByIdDesc();
	}
	
	public List<Wasserstand> findByDay(String day){
		return repo.findByDayOrderByHourASCMinASC(day);
	}
	
	public List<Wasserstand> findByMonth(String month){
		return repo.findByMonthOrderByDayASC(month);
	}
	
	public List<Wasserstand> findByYear(String year){
		return repo.findByYearOrderByMonthASC(year);
	}
	
	public <S extends Wasserstand> S save (S wasserstand) {
		return repo.save(wasserstand);
	}

}
