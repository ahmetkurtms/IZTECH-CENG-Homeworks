package dataAccesser;

import java.util.*;

class Country {
    String name;
    ArrayList<City> cities;
    ArrayList<Temperature> temperatures;

    // Constructor
    public Country(String name) {
        this.name = name;
        this.cities = new ArrayList<>();
        this.temperatures = addTemperature();
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public ArrayList<Temperature> getTemperatures() {
        return this.temperatures;
    }

    // Add a city to the list
    public void addCity(City city) {
        this.cities.add(city);
    }

    // Add a temperature to the list
    protected ArrayList<Temperature> addTemperature() {
    	String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	ArrayList<Temperature> temp= new ArrayList<>();
    	for (int year = 2020; year <= 2022; year++) {
    	    for (String month : months) {
    	        double celciusMeasurement = Math.random() * 40 - 20;

    	        Temperature temperature = new Temperature(year, month, celciusMeasurement);
    	        temp.add(temperature);
    	        
    	    }
    	}
		return temp;
    }
}
