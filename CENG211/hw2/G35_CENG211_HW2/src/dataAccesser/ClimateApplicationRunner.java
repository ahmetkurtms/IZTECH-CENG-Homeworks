package dataAccesser;

import java.util.ArrayList;

public class ClimateApplicationRunner {
    public static void main(String[] args) {
        // Create a ClimateRecord instance

        ClimateRecord climateRecord = new ClimateRecord();

        // TODO: Add countries and cities to the climateRecord
        // You might read in data from a file or a database here
        System.out.println(climateRecord.getCountryByName("Istabul"));
        // Create a ClimateApp instance
        // You might print out the results or display them in a user interface here
    }
}

