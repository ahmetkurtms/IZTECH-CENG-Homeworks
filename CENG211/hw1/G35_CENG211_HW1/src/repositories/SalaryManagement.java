package repositories;

import java.io.IOException;

import models.ShopAssistant;
import utilities.FileIO;


/**
 * The SalaryManagement class is responsible for managing the shop assistants.
 * It provides functionalities to get and set the shop assistants.
 * The shop assistants are read from a file using the FileIO class.
 */
public class SalaryManagement {

    /**
     * An array of shop assistants.
     */
    private ShopAssistant[] shopAssistants;

    /**
     * Constructor for the SalaryManagement class.
     * It initializes the shopAssistants array by reading from a file.
     *
     * @throws IOException if there is an error reading the file.
     */
    public SalaryManagement() throws IOException {
        this.shopAssistants =  FileIO.readShopAssistants();
    }

    /**
     * This method is used to get the array of shop assistants.
     *
     * @return ShopAssistant[] an array of shop assistants.
     */
    public ShopAssistant[] getShopAssistants() {
        return shopAssistants;
    }

    /**
     * This method is used to set the array of shop assistants.
     *
     * @param shopAssistants an array of shop assistants.
     */
    public void setShopAssistants(ShopAssistant[] shopAssistants) {
        this.shopAssistants = shopAssistants;
    }
}
