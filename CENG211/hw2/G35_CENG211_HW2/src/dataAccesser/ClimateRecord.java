package dataAccesser;

import java.util.ArrayList;


public class ClimateRecord {
	
		FileIO file = new FileIO();
		String filePath = "countries_and_cities.csv";
		ArrayList<Country> countries = file.readCountries(filePath);
		ArrayList<City> cities = file.readCities(filePath);
	    
        public ClimateRecord() {
        	this.countries = countries;
	        this.cities = cities;
	    }

        
        
	    // Getter methods
	    public ArrayList<Country> getCountries() {
	        return this.countries;
	    }

	    public ArrayList<City> getCities() {
	        return this.cities;
	    }

	    // Add a country to the list
	    public void addCountry(Country country) {
	        this.countries.add(country);
	    }

	    // Add a city to the list
	    public void addCity(City city) {
	        this.cities.add(city);
	    }

	    // Get a country by name
	    public Country getCountryByName(String name) {
	        for (Country country : this.countries) {
	            if (country.name.equals(name)) {
	                return country;
	            }
	        }
	        return null;  // Return null if no match found
	    }

	    // Get a city by name
	    public City getCityByName(String name) {
	        for (City city : this.cities) {
	            if (city.name.equals(name)) {
	                return city;
	            }
	        }
	        return null;  // Return null if no match found
	    }

	}
