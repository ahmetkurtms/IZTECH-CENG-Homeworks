package dataAccesser;

import java.util.Random;

public class ClimateMeasurements {
	private int year;
	private String month;
	
	public ClimateMeasurements(int year, String month) {
		this.year = year;
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

}
