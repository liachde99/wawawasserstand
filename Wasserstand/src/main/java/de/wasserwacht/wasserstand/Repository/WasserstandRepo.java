package de.wasserwacht.wasserstand.Repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import de.wasserwacht.wasserstand.Entity.Wasserstand;

public interface WasserstandRepo extends Repository<Wasserstand, Long> {

	List<Wasserstand> findAll();
	Wasserstand findById(Long id);
	Wasserstand findTopByOrderByIdDesc();
	
	<S extends Wasserstand> S save(S wasserstand);
}
