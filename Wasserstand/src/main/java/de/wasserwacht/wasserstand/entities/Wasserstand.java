package de.wasserwacht.wasserstand.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wasserstand {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private int stand;
	private String day;
	private String month;
	private String year;
	private String hour;
	private String min;
	
	public Wasserstand() {};
	public Wasserstand(int wasserstand) {
		day = new SimpleDateFormat("dd").format(new Date());
		month = new SimpleDateFormat("MM").format(new Date());
		year = new SimpleDateFormat("YYYY").format(new Date());
		hour = new SimpleDateFormat("hh").format(new Date());
		min = new SimpleDateFormat("mm").format(new Date());
		this.stand = wasserstand;
	}
	
	public String getDay() {return day;}
	public void setDay(String day) {this.day = day;}
	
	public String getHour() {return hour;}
	public void setHour(String hour) {this.hour = hour;}
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	public String getMin() {return min;}
	public void setMin(String min) {this.min = min;}
	
	public String getMonth() {return month;}
	public void setMonth(String month) {this.month = month;}
	
	public int getStand() {return stand;}
	public void setStand(int stand) {this.stand = stand;}
	
	public String getYear() {return year;}
	public void setYear(String year) {this.year = year;}
}
