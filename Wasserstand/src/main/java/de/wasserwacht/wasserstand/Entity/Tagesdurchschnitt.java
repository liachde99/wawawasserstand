package de.wasserwacht.wasserstand.Entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tagesdurchschnitt")
public class Tagesdurchschnitt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "wasserstand", nullable = false)
	private int wasserstand;
	
	@Column(name ="day")
	private int day;
	 
	@Column(name="month")
	private int month;
	
	@Column(name="year")
	private int year;

	@Column(name="week")
	private int week;
	
	
	public Tagesdurchschnitt() {};
	
	public Tagesdurchschnitt(int stand) {
		this.wasserstand = stand;
		
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		
		this.day = date.getDayOfMonth();
		this.month = date.getMonthValue();
		this.year = date.getYear();
		this.week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		
	}
	
	public Tagesdurchschnitt(int stand, int day, int month, int year, int week) {
		this.day = day;
		this.week = week;
		this.month = month;
		this.year = year;
		this.wasserstand = stand;
	}
	
	public Long getId() {return id;}
	public int getWasserstand() {return wasserstand;}
	public int getDay() {return day;}
	public int getWeek() {return week;}
	public int getMonth() {return month;}
	public int getYear() {return year;}
}
