package dataAccesser;

public class WindSpeed extends ClimateMeasurements{
	private double metersPerSecond;
	private double kmPerHour;
	
	public WindSpeed(int year, String month, double metersPerSecond) {
        super(year, month);
        this.metersPerSecond = metersPerSecond;
        this.kmPerHour = calculateKmPerHour(metersPerSecond);
    }

    private double calculateKmPerHour(double metersPerSecond) {
        return metersPerSecond * 3.6;
    }

	public double getMetersPerSecond() {
		return metersPerSecond;
	}

	public double getKmPerHour() {
		return kmPerHour;
	}
    
    

}
