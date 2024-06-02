package dataAccesser;

import java.util.ArrayList;

class City {
    String name;
    ArrayList<Temperature> temperatures;
    ArrayList<WindSpeed> windSpeeds;
    ArrayList<Humidity> humidities;
    ArrayList<RadiationAbsorbtion> radiationAbsorbtions;

    // Constructor
    public City(String name) {
        this.name = name;
        this.temperatures = addTemperature();
        this.windSpeeds = addWindSpeed();
        this.humidities = addHumidity();
        this.radiationAbsorbtions = addRadiationAbsorbtion();
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public ArrayList<Temperature> getTemperatures() {
        return this.temperatures;
    }

    public ArrayList<WindSpeed> getWindSpeeds() {
        return this.windSpeeds;
    }

    public ArrayList<Humidity> getHumidities() {
        return this.humidities;
    }

    public ArrayList<RadiationAbsorbtion> getRadiationAbsorbtions() {
        return this.radiationAbsorbtions;
    }

    // Add a temperature to the list
    public void addTemperature(Temperature temperature) {
        this.temperatures.add(temperature);
    }

    // Add a wind speed to the list
    public void addWindSpeed(WindSpeed windSpeed) {
        this.windSpeeds.add(windSpeed);
    }

    // Add a humidity to the list
    public void addHumidity(Humidity humidity) {
        this.humidities.add(humidity);
    }

    // Add a radiation absorption to the list
    public void addRadiationAbsorbtion(RadiationAbsorbtion radiationAbsorbtion) {
        this.radiationAbsorbtions.add(radiationAbsorbtion);
    }

    private ArrayList<Temperature> addTemperature() {
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
    
    private ArrayList<WindSpeed> addWindSpeed() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayList<WindSpeed> windSpeeds = new ArrayList<>();
        for (int year = 2020; year <= 2022; year++) {
            for (String month : months) {
                double metersPerSecond = Math.random() * 113.2;

                WindSpeed windSpeed = new WindSpeed(year, month, metersPerSecond);
                windSpeeds.add(windSpeed);
            }
        }
        return windSpeeds;
    }
    
    private ArrayList<Humidity> addHumidity() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayList<Humidity> humidities = new ArrayList<>();
        for (int year = 2020; year <= 2022; year++) {
            for (String month : months) {
                double humidityPercentage = Math.random() * 100.0;

                Humidity humidity = new Humidity(year, month, humidityPercentage);
                humidities.add(humidity);
            }
        }
        return humidities;
    }
    
    private ArrayList<RadiationAbsorbtion> addRadiationAbsorbtion() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayList<RadiationAbsorbtion> radiationAbsorbtions = new ArrayList<>();
        for (int year = 2020; year <= 2022; year++) {
            for (String month : months) {
                double unitAbsorbtionValue = Math.random() * (20.0 - 5.0) + 5.0;
                
           
                RadiationIntensity radiationIntensity = RadiationIntensity.HIGH;
                //BURADA SADECE HIGH YAZILI BU RASTGELE SEÇİLMELİ!!!

                RadiationAbsorbtion radiationAbsorbtion = new RadiationAbsorbtion(year, month, radiationIntensity, unitAbsorbtionValue);
                radiationAbsorbtions.add(radiationAbsorbtion);
            }
        }
        return radiationAbsorbtions;
    }



}

