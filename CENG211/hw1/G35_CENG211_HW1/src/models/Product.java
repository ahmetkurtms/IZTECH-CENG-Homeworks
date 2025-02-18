package models;


public class Product {
    // Fields
	private int ID;
	private String productName;
	private double price;

    // Constructor
	public Product(int ID, String productName, double price) {
	    this.ID = ID;
	    this.productName = productName;
	    this.price = price;
	}

    // Getters and Setters
	public int getID() {
	    return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
