package service;

import models.*;


/**
 * The Query class provides methods to perform various queries on transactions and shop assistants.
 * It includes methods to find the transaction with the highest total price, the most expensive product in the lowest price transaction,
 * the lowest transaction fee, the highest-salary shop assistant, the total revenue, and the total profit.
 */
public class Query {
    /**
     * Finds the transaction with the highest total price.
     *
     * @param allTransactions an array of all transactions
     * @return the transaction with the highest total price
     */
    public static Transaction findHighestTotalPriceTransaction(Transaction[] allTransactions) {
        Transaction highestTotalPriceTransaction = allTransactions[0];
        for (Transaction transaction : allTransactions) {
            if (transaction.getTotalPrice() > highestTotalPriceTransaction.getTotalPrice()) {
                highestTotalPriceTransaction = transaction;
            }
        }
        return highestTotalPriceTransaction;
    }

    /**
     * Finds the most expensive product in the lowest price transaction.
     *
     * @param allTransactions an array of all transactions
     * @return the most expensive product in the lowest price transaction
     */
    public static Product findMostExpensiveProductInLowestPriceTransaction(Transaction[] allTransactions) {
        Transaction lowestPriceTransaction = allTransactions[0];
        for (Transaction transaction : allTransactions) {
            if (transaction.getTotalPrice() < lowestPriceTransaction.getTotalPrice()) {
                lowestPriceTransaction = transaction;
            }
        }
        Product mostExpensiveProduct = allTransactions[0].getProducts()[0];
        for (Product product : lowestPriceTransaction.getProducts()) {
            if (product.getPrice() > mostExpensiveProduct.getPrice()) {
                mostExpensiveProduct = product;
            }
        }
        return mostExpensiveProduct;
    }

    /**
     * Finds the lowest transaction fee among all transactions.
     *
     * @param allTransactions an array of all transactions
     * @return the lowest transaction fee
     */
    public static double findLowestTransactionFee(Transaction[] allTransactions) {
        double lowestTransactionFee = allTransactions[0].getTransactionFee();
        for (Transaction transaction : allTransactions) {
            if (transaction.getTransactionFee() < lowestTransactionFee) {
                lowestTransactionFee = transaction.getTransactionFee();
            }
        }
        return lowestTransactionFee;
    }

    /**
     * Finds the shop assistant with the highest salary.
     *
     * @param transactionsOfAssistants a 2D array of transactions, where each row corresponds to a shop assistant
     * @param assistants an array of all shop assistants
     * @return the shop assistant with the highest salary
     */
    public static ShopAssistant findHighestSalaryShopAssistant(Transaction[][] transactionsOfAssistants, ShopAssistant[] assistants) {
        double highestTotalIncome = 0;
        int indexOfHighestSalaryAssistant = 0;

        for (int i = 0; i < 100; i++){
            Transaction[] currentTransactions = transactionsOfAssistants[i];
            double currentTotalRevenue = 0;
            for (Transaction transaction : currentTransactions) {
                currentTotalRevenue += transaction.getTotalPrice() - transaction.getTransactionFee();
            }

            if (currentTotalRevenue > 7500) {
                currentTotalRevenue *= 0.03;
            } else {
                currentTotalRevenue *= 0.01;
            }
            assistants[i].setCommision(currentTotalRevenue);
           
            assistants[i].setTotalSalary(assistants[i].getWeeklySalary()*4 + assistants[i].getCommision());

            if (assistants[i].getTotalSalary() > highestTotalIncome) {
                highestTotalIncome = assistants[i].getTotalSalary();
                indexOfHighestSalaryAssistant = i;
            }
        }

        return assistants[indexOfHighestSalaryAssistant];      
    }

    /**
     * Calculates the total revenue from all transactions.
     *
     * @param transactionsOfAssistants a 2D array of transactions, where each row corresponds to a shop assistant
     * @return the total revenue
     */
    public static int findTotalRevenue(Transaction[][] transactionsOfAssistants) {
        int totalRevenue = 0;
        for (Transaction[] transactions :  transactionsOfAssistants) {
           for (Transaction transaction : transactions) {
               totalRevenue += transaction.getTotalPrice() - transaction.getTransactionFee();
           }
        }
        return totalRevenue;
    }

    /**
     * Calculates the total profit after paying the shop assistant salaries.
     *
     * @param transactionsOfAssistants a 2D array of transactions, where each row corresponds to a shop assistant
     * @param assistants an array of all shop assistants
     * @return the total profit
     */
    public static int findTotalProfit(Transaction[][] transactionsOfAssistants, ShopAssistant[] assistants) {
        int totalExpenseOfAssistants = 0;

        for (int i = 0; i < 100; ++i){
            Transaction[] currentTransactions = transactionsOfAssistants[i];
            double currentTotalRevenue = 0;
            for (Transaction transaction : currentTransactions) {
                currentTotalRevenue += transaction.getTotalPrice() - transaction.getTransactionFee();
            }

            if (currentTotalRevenue > 7500) {
                currentTotalRevenue *= 0.03;
            } else {
                currentTotalRevenue *= 0.01;
            }

            int totalIncomeOfCurrentAssistant = assistants[i].getWeeklySalary()*4 + (int) currentTotalRevenue;
            totalExpenseOfAssistants += totalIncomeOfCurrentAssistant;
        }

        return findTotalRevenue(transactionsOfAssistants) - totalExpenseOfAssistants;
    }
}
