package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;

public interface LastsevendaysRepo extends CrudRepository<Monatsdurchschnitt, Long> {
	
	Optional<Monatsdurchschnitt> findById(Long id);
	List<Monatsdurchschnitt> findByTageswasserstand_id(int id);
	void truncateLastsevendays();
	
	<S extends Lastsevendays> S save(S lastsevendays);
	
}

