package models;


public class Transaction {
    // Fields
	private int ID;
	private Product[] products;
	private double totalPrice;
	private double transactionFee;
	
    // Constructor
	public Transaction(int id, Product[] products, double totalPrice, double transactionFee) {
		ID = id;
		this.products = products;
		this.totalPrice = totalPrice;
		this.transactionFee = transactionFee;
	}
    
    // Getters and Setters
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Product[] getProducts() {
		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}
}
