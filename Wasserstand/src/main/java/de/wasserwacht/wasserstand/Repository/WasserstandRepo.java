package de.wasserwacht.wasserstand.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wasserwacht.wasserstand.Entity.Wasserstand;

public interface WasserstandRepo extends CrudRepository<Wasserstand, Long> {
	
	Optional<Wasserstand> findById(Long id);
	Wasserstand findTopByOrderByIdDesc();
	
}

