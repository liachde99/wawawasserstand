package de.wasserwacht.wasserstand.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Wasserstand")
public class Wasserstand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private int wasserstand;
	private char[] day;
	private char[] month;
	private char[] year;
	private char[] hour;
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
