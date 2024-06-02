package dataAccesser;

enum RadiationIntensity { LOW, MEDIUM, HIGH
}

public class RadiationAbsorbtion extends ClimateMeasurements{
	RadiationIntensity radiationIntensity;
	private double unitAbsorptionValue;
	
	public RadiationAbsorbtion(int year, String month,RadiationIntensity radiationIntensity, double unitAbsorptionValue) {
		super(year,month);
		this.radiationIntensity = radiationIntensity;
		this.unitAbsorptionValue = unitAbsorptionValue;
	}

}
