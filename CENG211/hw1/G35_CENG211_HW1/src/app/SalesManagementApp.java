package app;

import java.io.IOException;

import models.*;
import repositories.*;
import service.*;

public class SalesManagementApp {
	public static void main(String[] args) throws IOException {
		// Create necessary objects for the application.
		TransactionManagement tm = new TransactionManagement();
		SalaryManagement sm = new SalaryManagement();

		// Generate transactions and shop assistants.
		Transaction[][] transactionsOfAssistants = tm.getTransactionsOfAssistants();
		Transaction[] allTransactions = tm.getAllTransactions();
		ShopAssistant[] assistants = sm.getShopAssistants();

		// 1- The highest-total-price transaction.
		Transaction highestTotalPriceTransaction = Query.findHighestTotalPriceTransaction(allTransactions);
		System.out.println(String.format("The transaction with the highest total price is: %.2f TL", highestTotalPriceTransaction.getTotalPrice()));

		// 2- The most expensive product in the lowest-price transaction.
		Product mostExpensiveProductInLowestPriceTransaction = Query.findMostExpensiveProductInLowestPriceTransaction(allTransactions);
		System.out.println(String.format("The most expensive product in the lowest price transaction is: " + mostExpensiveProductInLowestPriceTransaction.getProductName() + ", " + "%.2f TL", mostExpensiveProductInLowestPriceTransaction.getPrice()));

		// 3- The lowest transaction fee.
		double lowestTransactionFee = Query.findLowestTransactionFee(allTransactions);
		System.out.println(String.format("The lowest transaction fee is: %.2f TL", lowestTransactionFee));

		// 4- The highest-salary shop assistant. (Including his/her ID, name, seniority, weekly basis salary, commission, and total salary)
		ShopAssistant highestSalaryShopAssistant = Query.findHighestSalaryShopAssistant(transactionsOfAssistants, assistants);
		System.out.println("The highest-salary shop assistant's informations are: ID: " + highestSalaryShopAssistant.getId() + ", Name-Surname: " + highestSalaryShopAssistant.getName() + " " + highestSalaryShopAssistant.getSurname() + ", Seniority: " + highestSalaryShopAssistant.getSeniorityInYears() + " years, Weekly basis salary: " + highestSalaryShopAssistant.getWeeklySalary() + " TL, Commission: " + String.format("%.2f", highestSalaryShopAssistant.getCommision()) + " TL, Total Salary: " +  String.format("%.2f", highestSalaryShopAssistant.getTotalSalary()) + " TL");

		// 5- The total revenue that is earned from 1500 transactions including both total price and transaction fee of each transaction.
		int totalRevenue = Query.findTotalRevenue(transactionsOfAssistants);
		System.out.println("The total revenue is: " + totalRevenue + " TL");

		// 6- The total profit that is earned after paying the shop assistant salaries.
		int totalProfit = Query.findTotalProfit(transactionsOfAssistants, assistants);
		System.out.println("The total profit is: " + totalProfit + " TL");
	}
}
