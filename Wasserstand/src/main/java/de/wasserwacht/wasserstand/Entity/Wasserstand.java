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
@Table(name = "wasserstand")
public class Wasserstand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name="hour")
	private int hour;
	
	@Column(name="min")
	private int min;
	
	public Wasserstand() {};
	
	public Wasserstand(int stand) {
		this.wasserstand = stand;
		
		LocalDateTime date = LocalDateTime.now(ZoneId.of("CET"));
		
		this.day = date.getDayOfMonth();
		this.month = date.getMonthValue();
		this.year = date.getYear();
		this.hour = date.getHour();
		this.min = date.getMinute();
		
	}
	
	public int getWasserstand() {return wasserstand;}
	public void setWasserstand(int wasserstand) {this.wasserstand = wasserstand;}
	
	public int getDay() {return day;}
	public int getHour() {return hour;}
	public int getMin() {return min;}
	public int getMonth() {return month;}
	public int getYear() {return year;}
	
	public String stamp() {
		return getDay() + "." + getMonth() + " - " + getHour() + ":" + getMin();
	}
	
}
