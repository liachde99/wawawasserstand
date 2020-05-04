package de.wasserwacht.wasserstand.Repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.wasserwacht.wasserstand.Entity.Tagesdurchschnitt;

public interface TagesdurchschnittRepo extends Repository<Tagesdurchschnitt, Long> {
	
	Tagesdurchschnitt findById(Long id);
	List<Tagesdurchschnitt> findByMonthAndYear(int month,int year);
	List<Tagesdurchschnitt> findByWeek(int week);
	Tagesdurchschnitt findByDayAndMonthAndYear(int day, int month, int year);
	
	<S extends Tagesdurchschnitt> S save(S wasserstand);
	
}

