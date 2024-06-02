package dataAccesser;

public class Humidity extends ClimateMeasurements{
	private double humidityPercentage;
	
	public Humidity(int year, String month, double humidityPercentage) {
        super(year, month);
        this.humidityPercentage = humidityPercentage;
    }

}
