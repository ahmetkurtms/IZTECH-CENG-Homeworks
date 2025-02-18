package dataAccesser;

import java.util.Scanner;

public class ClimateApp {
    public static void main(String[] args) {
        ClimateRecord climateRecord = new ClimateRecord();
//        System.out.println(climateRecord.cities.get(0).getTemperatures().get(0).getCelciusMeasurement());

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            printMenu();
            System.out.print("Please select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Dummy read to consume the newline character

            switch (option) {
                case 1:
                    calculateAverageTemperatureForCountry(climateRecord);
                    break;
                case 2:
                    calculateAverageTemperatureForCity(climateRecord);
                    break;
                case 3:
                    calculateAverageWindSpeedForCity(climateRecord);
                    break;
                case 4:
                    calculateAverageHumidityForCity(climateRecord);
                    break;
                case 5:
                    countRadiationIntensityForCity(climateRecord);
                    break;
                case 6:
                    calculateFeltTemperatureForCity(climateRecord);
                    break;
                case 7:
                    System.out.println("==> Closing the application...");
                    break;
                default:
                    System.out.println("Incorrect option input! Please reenter another option input.");
                    break;
            }

        } while (option != 7);
    }

    private static void printMenu() {
        System.out.println("[1] Calculate average temperature for a country according to temperature unit and year.");
        System.out.println("[2] Calculate average temperature for a city according to temperature unit and year.");
        System.out.println("[3] Calculate average wind speed for a city according to speed unit and year.");
        System.out.println("[4] Calculate average humidity of a city for every year.");
        System.out.println("[5] Count how many times a year a specific radiation intensity value appears for a city.");
        System.out.println("[6] Calculate the 'felt temperature' value of a city for a specific month and year.");
        System.out.println("[7] Exit the application.");
    }

    // Calculate average temperature for a country
    private static void calculateAverageTemperatureForCountry(ClimateRecord climateRecord) {
        // Implementation goes here...
    }

    // Calculate average temperature for a city
    private static void calculateAverageTemperatureForCity(ClimateRecord climateRecord) {
        // Implementation goes here...
    }

    // Calculate average wind speed for a city
    private static void calculateAverageWindSpeedForCity(ClimateRecord climateRecord) {
        // Implementation goes here...
    }

    // Calculate average humidity for a city
    private static void calculateAverageHumidityForCity(ClimateRecord climateRecord) {
        // Implementation goes here...
    }

    // Count radiation intensity occurrences for a city
    private static void countRadiationIntensityForCity(ClimateRecord climateRecord) {
        // Implementation goes here...
    }

    // Calculate felt temperature for a city
    private static void calculateFeltTemperatureForCity(ClimateRecord climateRecord) {
        // Implementation goes here...
    }
}




