package de.wasserwacht.wasserstand.Entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
	
	@SuppressWarnings("deprecation")
	public Wasserstand(int stand) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		Date datum = new Date();
		String dat = datum.toString();
		datum = sdf.parse(dat);
		
		day = datum.getDay();
		month = datum.getMonth();
		year = datum.getYear();
		hour = datum.getHours();
		min = datum.getMinutes();
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
