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
	
	public Wasserstand(int stand, int day, int month, int year, int hour, int min) {
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.month = month;
		this.year = year;
		this.wasserstand = stand;
	}
	
	public int getWasserstand() {return wasserstand;}
	public int getDay() {return day;}
	public int getHour() {return hour;}
	public int getMin() {return min;}
	public int getMonth() {return month;}
	public int getYear() {return year;}
	
	public String stamp() {
		
		String day, month, hour, min;
		if(getDay()<10) {
			day = "0" + getDay();
		}else {
			day = getDay() + "";
		}
		
		if(getMonth()<10) {
			month = "0" + getMonth();
		}else {
			month = getMonth() + "";
		}
		
		if(getHour()<10) {
			hour = "0" + getHour();
		}else {
			hour = getHour() + "";
		}
		
		if(getDay()<10) {
			min = "0" + getMin();
		}else {
			min = getMin() + "";
		}
		return day + "." + month + " - " + hour + ":" + min;
	}
	
}
