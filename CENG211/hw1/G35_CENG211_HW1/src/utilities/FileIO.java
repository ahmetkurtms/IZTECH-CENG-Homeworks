package utilities;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import models.Product;
import models.ShopAssistant;


public class FileIO {
		
	public static final int DEFAULT_CAPACITY = 90;

	public static Product[]  readProducts() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/resources/products.csv"));
			
			String line;
			int capacity = 0;
				
			Product[] productArr = new Product[DEFAULT_CAPACITY];
	 			
			int index = 0;
				
			while ((line = reader.readLine()) != null) {
				productArr = checkCapacity(capacity, productArr);
					
				int id = Integer.parseInt(line.split(";")[0]);
				String name = line.split(";")[1];
				double price = Double.parseDouble((line.split(";")[2]).replace(",", "."));
					
				Product product = new Product(id, name, price);
					
				productArr[index] = product;
				capacity++;
				index++;
			}
				
			reader.close();
			return productArr;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Changes array size with calling {@link #changeCapacity(Product[])}
	 * @param capacity for control if we exceed the array size limit
	 * @param productArr which we want to change array size
	 * @return new product array with bigger size
	 */
	private static Product[] checkCapacity(int capacity, Product[] productArr) {
		if (capacity >= DEFAULT_CAPACITY) {
			productArr = changeCapacity(productArr);
		}
		
		return productArr;
	}

	/**
	 * Called from {@link #checkCapacity(int, Product[])}
	 * Changes size of the array with copying it in bigger array
	 * @param productArr array which we want to make it bigger
	 * @return new bigger product array
	 */
	private static Product[] changeCapacity(Product[] productArr) {
		int newCapacity = DEFAULT_CAPACITY*2;
		Product[] newArr  = new Product[newCapacity];
		
		for(int i=0; i < productArr.length; i++) {
			newArr[i] = productArr[i];
		}
		
		return newArr;
	}
	
	public static ShopAssistant[]  readShopAssistants() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/resources/shopAssistants.csv"));
			
			String line;
			int capacity = 0;
				
				
				
			ShopAssistant[] shopAssistantArr = new ShopAssistant[DEFAULT_CAPACITY];
	 			
			int index = 0;
				
			while ((line = reader.readLine()) != null) {
				shopAssistantArr = checkCapacity(capacity, shopAssistantArr);
					
				int id = Integer.parseInt(line.split(";")[0]);
				String name = line.split(";")[1];
				String surname = line.split(";")[2];
				String phoneNumber = line.split(";")[3];
				
					
				ShopAssistant shopAssistant = new ShopAssistant(id, name, surname, phoneNumber);
					
				shopAssistantArr[index] = shopAssistant;
				capacity++;
				index++;
			}
				
			reader.close();
			return shopAssistantArr;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private static ShopAssistant[] checkCapacity(int capacity, ShopAssistant[] shopAssistantArr) {
		if (capacity >= DEFAULT_CAPACITY) {
			shopAssistantArr = changeCapacity(shopAssistantArr);
		}
		
		return shopAssistantArr;
	}

	/**
	 * Called from {@link #checkCapacity(int, Product[])}
	 * Changes size of the array with copying it in bigger array
	 * @param productArr array which we want to make it bigger
	 * @return new bigger product array
	 */
	private static ShopAssistant[] changeCapacity(ShopAssistant[] shopAssistantArr) {
		int newCapacity = DEFAULT_CAPACITY*2;
		ShopAssistant[] newArr  = new ShopAssistant[newCapacity];
		
		for(int i=0; i < shopAssistantArr.length; i++) {
			newArr[i] = shopAssistantArr[i];
		}
		
		return newArr;
	}
}
