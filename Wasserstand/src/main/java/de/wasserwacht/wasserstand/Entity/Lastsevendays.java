package de.wasserwacht.wasserstand.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lastsevendays")
public class Lastsevendays {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "tageswasserstandid", nullable = false)
	private Long tageswasserstandid;
	
	
	public Lastsevendays() {};
	
	public Lastsevendays(Long id) {
		this.tageswasserstandid = id;
	}
	
	public Long getTagesWasserstanId() {return tageswasserstandid;}
}
