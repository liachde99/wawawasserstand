package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;
import de.wasserwacht.wasserstand.Entity.Monatsdurchschnitt;

public interface LastsevendaysRepo extends CrudRepository<Lastsevendays, Long> {
	
	Optional<Lastsevendays> findById(Long id);
	List<Lastsevendays> findByTageswasserstand_id(Long id);
	void truncateLastsevendays();
	
	<S extends Lastsevendays> S save(S lastsevendays);
	
}

