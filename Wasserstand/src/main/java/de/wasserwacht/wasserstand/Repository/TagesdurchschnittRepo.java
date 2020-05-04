package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;

public interface TagesdurchschnittRepo extends CrudRepository<Tagesdurchschnitt, Long> {
	
	Optional<Tagesdurchschnitt> findById(Long id);
	List<Tagesdurchschnitt> findByMonthAndYear(int month,int year);
	List<Tagesdurchschnitt> findByWeek(int week);
	Tagesdurchschnitt findByDayAndMonthAndYear(int day, int month, int year);
	
	<S extends Tagesdurchschnitt> S save(S wasserstand);
	
}

