package de.wasserwacht.wasserstand.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wasserwacht.wasserstand.Entity.Wasserstand;
import de.wasserwacht.wasserstand.Repository.WasserstandRepo;

@Service
public class WasserstandService implements WasserstandRepo{
	
	@Autowired
	private WasserstandRepo repo;

	@Override
	public List<Wasserstand> findAll() {
		return repo.findAll();
	}

	@Override
	public Wasserstand findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public Wasserstand findTopByOrderByIdDesc() {
		return repo.findTopByOrderByIdDesc();
	}
	
}
