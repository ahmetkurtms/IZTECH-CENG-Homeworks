package dataAccesser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
    public ArrayList<Country> readCountries(String filePath) {
        ArrayList<Country> countries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String countryName = parts[0].trim();
                Country country = new Country(countryName);
                countries.add(country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public ArrayList<City> readCities(String filePath) {
        ArrayList<City> cities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                for (int i = 1; i < parts.length; i++) {
                    String cityName = parts[i].trim();
                    City city = new City(cityName);
                    cities.add(city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }
}





