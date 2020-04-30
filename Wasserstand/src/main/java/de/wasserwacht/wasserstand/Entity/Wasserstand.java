package de.wasserwacht.wasserstand.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
	private String day;
	
	@Column(name="month")
	private String month;
	
	@Column(name="year")
	private String year;

	@Column(name="hour")
	private String hour;
	
	@Column(name="min")
	private String min;
	
	public Wasserstand() {};
	
	public Wasserstand(int stand) {
		this.wasserstand = stand;
		SimpleDateFormat dayformat = new SimpleDateFormat("dd");
		dayformat.setTimeZone(TimeZone.getTimeZone("GMT+2"));

		SimpleDateFormat monthformat = new SimpleDateFormat("MM");
		monthformat.setTimeZone(TimeZone.getTimeZone("GMT+2"));

		SimpleDateFormat yearformat = new SimpleDateFormat("YYYY");
		yearformat.setTimeZone(TimeZone.getTimeZone("GMT+2"));

		SimpleDateFormat hourformat = new SimpleDateFormat("HH");
		hourformat.setTimeZone(TimeZone.getTimeZone("GMT+2"));

		SimpleDateFormat minformat = new SimpleDateFormat("mm");
		minformat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		
		day = dayformat.format(new Date());
		month = monthformat.format(new Date());
		year = yearformat.format(new Date());
		hour = hourformat.format(new Date());
		min = minformat.format(new Date());
	}
	
	public int getWasserstand() {return wasserstand;}
	public void setWasserstand(int wasserstand) {this.wasserstand = wasserstand;}
	
	public String getDay() {return day;}
	public String getHour() {return hour;}
	public String getMin() {return min;}
	public String getMonth() {return month;}
	public String getYear() {return year;}
	
	public String stamp() {
		return getDay().toString() + "." + getMonth().toString() + " - " + getHour().toString() + ":" + getMin().toString();
	}
	
}
