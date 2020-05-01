package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Wasserstand;

public interface WasserstandRepo extends CrudRepository<Wasserstand, Long> {
	
	Optional<Wasserstand> findById(Long id);
	Wasserstand findTopByOrderByIdDesc();
	List<Wasserstand> findByDayAndMonthAndYear(int day,int month,int year);
	List<Wasserstand> findByMonthAndYear(int month,int year);
	List<Wasserstand> findByYear(int year);
	List<Wasserstand> findByWeek(int week);
	
	<S extends Wasserstand> S save(S wasserstand);
	
}

