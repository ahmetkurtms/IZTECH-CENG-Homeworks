package dataAccesser;

public class Temperature extends ClimateMeasurements {
	double celciusMeasurement;
    double fahrenheitMeasurement;
    double kelvinMeasurement;

    // Constructor
    public Temperature(int year, String month, double celciusMeasurement) {
        super(year,month);
        this.celciusMeasurement = celciusMeasurement;
        this.fahrenheitMeasurement = (celciusMeasurement * 9.0 / 5.0) + 32.0;
        this.kelvinMeasurement = celciusMeasurement + 273.15;
    }

    // Getter methods
    public double getCelciusMeasurement() {
        return this.celciusMeasurement;
    }

    public double getFahrenheitMeasurement() {
        return this.fahrenheitMeasurement;
    }

    public double getKelvinMeasurement() {
        return this.kelvinMeasurement;
    }
}
