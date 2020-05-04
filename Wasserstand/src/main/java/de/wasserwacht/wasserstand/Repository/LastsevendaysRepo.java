package de.wasserwacht.wasserstand.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Lastsevendays;

public interface LastsevendaysRepo extends CrudRepository<Lastsevendays, Long> {
	
	Optional<Lastsevendays> findById(Long id);
	List<Lastsevendays> findByTageswasserstandid(Long id);
	List<Lastsevendays> findAll();
	void deleteAll();
	
	<S extends Lastsevendays> S save(S lastsevendays);
	
}

