package de.wasserwacht.wasserstand.Entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "monatsdurchschnitt")
public class Monatsdurchschnitt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "wasserstand", nullable = false)
	private int wasserstand;
	
	@NotNull
	@Column(name = "temperatur", nullable = false)
	private double temperatur;

	@Column(name="month")
	private int month;
	
	@Column(name="year")
	private int year;
	
	public Monatsdurchschnitt() {};
	
	public Monatsdurchschnitt(int stand,double temp) {
		this.wasserstand = stand;
		this.temperatur = temp;
		
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		
		this.month = date.getMonthValue();
		this.year = date.getYear();
		
	}
	
	public Monatsdurchschnitt(int stand, double temp, int month, int year) {
		this.month = month;
		this.year = year;
		this.wasserstand = stand;
		this.temperatur = temp;
	}
	
	public int getWasserstand() {return wasserstand;}
	public int getMonth() {return month;}
	public int getYear() {return year;}
	public double getTemperatur() {return temperatur;}
}
