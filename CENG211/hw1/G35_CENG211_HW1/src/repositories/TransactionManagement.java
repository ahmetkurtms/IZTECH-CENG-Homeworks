package repositories;

import java.util.Random;
import java.io.IOException;

import models.Product;
import models.Transaction;
import utilities.FileIO;


public class TransactionManagement {
	
	private Transaction[] allTransactions = new Transaction[1500];
	private Transaction[][] transactionsOfAssistants = new Transaction[100][15];
	
	
	public TransactionManagement() throws IOException {
		createTransactions();
		distributeTransactions();
	}

	private void createTransactions() throws IOException {
		
		Random random = new Random();
		Product[] products = FileIO.readProducts();	
		
        for (int i = 0; i < 1500; i++) {
            int[] selectedIndices = new int[3];
            Product[] selectedProducts = new Product[3];
            double totalPrice=0;

            for (int j = 0; j < 3; j++) {
                int randomIndex;
                boolean isDuplicate;
                
                do {
                    randomIndex = random.nextInt(products.length);
                    isDuplicate = false;

                    for (int k = 0; k < j; k++) {
                        if (selectedIndices[k] == randomIndex) {
                            isDuplicate = true;
                            break;
                        }
                    }
                } while (isDuplicate);

                selectedIndices[j] = randomIndex;
                selectedProducts[j] = products[randomIndex];
                
                int randomQuantity = random.nextInt(10) + 1;
                totalPrice += randomQuantity * products[randomIndex].getPrice();
                
            }
            
            double transactionFee;
            if(totalPrice <= 499) {
            	transactionFee = totalPrice * 0.01;
            }else if(totalPrice >= 500 && totalPrice <= 799) {
            	transactionFee = totalPrice * 0.03;
            }else if(totalPrice >= 800 && totalPrice <= 999) {
            	transactionFee = totalPrice * 0.05;
            }else {
            	transactionFee = totalPrice * 0.09;
            }
            
            Transaction transaction = new Transaction(i, selectedProducts, totalPrice, transactionFee);
            
            
            allTransactions[i] = transaction;
        }
	}
	
	private void distributeTransactions() {
		int index = 0;
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 15; col++) {
            	transactionsOfAssistants[row][col] = allTransactions[index];
                index++;
            }
        }
	}

	public Transaction[][] getTransactionsOfAssistants() {
		return transactionsOfAssistants;
	}

	public void setTransactionsOfAssistants(Transaction[][] transactionsOfAssistants) {
		this.transactionsOfAssistants = transactionsOfAssistants;
	}

    public Transaction[] getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(Transaction[] allTransactions) {
        this.allTransactions = allTransactions;
    }
}
