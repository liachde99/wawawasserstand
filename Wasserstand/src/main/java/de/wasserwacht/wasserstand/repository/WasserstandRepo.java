package de.wasserwacht.wasserstand.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.wasserwacht.wasserstand.entities.Wasserstand;

public interface WasserstandRepo extends Repository<Wasserstand,Long>{
	Wasserstand findById(Long id);
	Wasserstand findTopByOrderByIdDesc();
	List<Wasserstand> findByDay(String day);
	List<Wasserstand> findByMonth(String month);
	List<Wasserstand> findByYear(String year);
	
	<S extends Wasserstand> S save(S wasserstand);
	
}
