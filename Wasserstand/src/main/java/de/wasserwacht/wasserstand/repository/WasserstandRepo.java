package de.wasserwacht.wasserstand.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.wasserwacht.wasserstand.entities.Wasserstand;

public interface WasserstandRepo extends Repository<Wasserstand,Long>{
	Wasserstand findById(Long id);
	Wasserstand findTopByOrderByIdDesc();
	List<Wasserstand> findByDayOrderByHourASC(String day);
	List<Wasserstand> findByMonthOrderByDayASC(String month);
	List<Wasserstand> findByYearOrderByMonthASC(String year);
	
	<S extends Wasserstand> S save(S wasserstand);
	
}
