package de.wasserwacht.wasserstand.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "wasserstand", nullable = false)
	private int wasserstand;
	
	@Column(name ="day")
	private char[] day;
	
	@Column(name="month")
	private char[] month;
	
	@Column(name="year")
	private char[] year;

	@Column(name="hour")
	private char[] hour;
	
	@Column(name="min")
	private char[] min;
	
	public Wasserstand() {};
	
	public Wasserstand(int stand) {
		this.wasserstand = stand;
		day = new SimpleDateFormat("dd").format(new Date()).toCharArray();
		month = new SimpleDateFormat("MM").format(new Date()).toCharArray();
		year = new SimpleDateFormat("YYYY").format(new Date()).toCharArray();
		hour = new SimpleDateFormat("hh").format(new Date()).toCharArray();
		min = new SimpleDateFormat("mm").format(new Date()).toCharArray();
	}
	
	public int getWasserstand() {return wasserstand;}
	public void setWasserstand(int wasserstand) {this.wasserstand = wasserstand;}
	
	public char[] getDay() {return day;}
	public char[] getHour() {return hour;}
	public char[] getMin() {return min;}
	public char[] getMonth() {return month;}
	public char[] getYear() {return year;}
	
	public String stamp() {
		return getDay().toString() + "." + getMonth().toString() + " - " + getHour().toString() + ":" + getMin().toString();
	}
	
}
