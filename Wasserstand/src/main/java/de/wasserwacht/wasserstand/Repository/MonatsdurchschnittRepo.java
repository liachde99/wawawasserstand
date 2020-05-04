package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;

public interface MonatsdurchschnittRepo extends CrudRepository<Monatsdurchschnitt, Long> {
	
	Optional<Monatsdurchschnitt> findById(Long id);
	List<Monatsdurchschnitt> findByYear(int year);
	
	<S extends Monatsdurchschnitt> S save(S monatsdurchschnitt);
	
}

