package models;

import java.util.Random;


public class ShopAssistant {
    // Fields
	private int id;
	private String name, surname, phoneNumber;
	private int seniorityInYears;
	private int weeklySalary;
	private double commision;
	private double totalSalary;

	// Constructor
	public ShopAssistant(int id, String name, String surname, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;

        // Since we are assigning these values to every ShopAssistant object,
        // doing this in the constructor would make it safer.
		this.seniorityInYears = new Random().nextInt(15);
		
		if(seniorityInYears < 1) {
			this.weeklySalary = 1500;
		}
		else if(seniorityInYears >= 1 && seniorityInYears < 3) {
			this.weeklySalary = 2000;
		}
		else if(seniorityInYears >= 3 && seniorityInYears < 5) {
			this.weeklySalary = 2500;
		}
		else {
			this.weeklySalary = 3000;
		}
	}

    // Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getSeniorityInYears() {
		return seniorityInYears;
	}

	public void setSeniorityInYears(int seniorityInYears) {
		this.seniorityInYears = seniorityInYears;
	}
	
	public int getWeeklySalary() {
		return weeklySalary;
	}

	public void setWeeklySalary(int weeklySalary) {
		this.weeklySalary = weeklySalary;
	}

	public double getCommision() {
		return commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
}
