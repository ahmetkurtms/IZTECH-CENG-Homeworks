package interfaces;



import classes.KeyValuePairArray;

/**
 * An interface for a bag data structure.
 *
 * @param <T> the type of items in the bag
 */
public interface IBag<T> {
    
    /**
     * Gets the size of the bag.
     * 
     * @return the size of the bag.
     */
    public int getSize();

    /**
     * Adds a new item to the bag.
     * 
     * @param newItem the item to add
     * @return true if the item was added successfully, false otherwise
     * 
     * Possibilities of false returns are if the bag is full or the newItem is null.
     */
    public boolean add(T newItem);
    
    /**
     * Checks if the bag is empty.
     * 
     * @return true if the bag is empty, false otherwise
     */
    public boolean isEmpty();
    
    /**
     * Checks if the bag is full.
     * 
     * @return true if the bag is full, false otherwise
     */
    public boolean isFull();
    
    /**
     * Removes the item at the specified index from the bag.
     * 
     * @param index the index of the item to remove
     * @return the removed item
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the number of items in the bag
     */
    public T removeByIndex(int index);
    
    /**
     * Removes a random item from the bag.
     * 
     * @return the removed item
     * @throws NoSuchElementException if the bag is empty
     */
    public T remove();
    
    /**
     * Removes the specified item from the bag.
     * 
     * @param item the item to remove
     * @return the removed item 
     * @throws NullPointerException if item is null
     * @throws NoSuchElementException if item was not found in the bag
     */
    public T remove(T item);
    
    /**
     * Returns the number of items in the bag.
     * 
     * @return the number of items in the bag
     */
    public int getItemCount();
    
    /**
     * Returns the index of the first occurrence of the specified item in the bag.
     * 
     * @param item the item to search for
     * @return the index of the first occurrence of the item, or -1 if the item was not found in the bag
     * @throws NullPointerException if item is null
     */
    public int getIndexOf(T item);
    
    /**
     * Checks if the bag contains the specified item.
     * 
     * @param item the item to search for
     * @return true if the item was found in the bag, false otherwise
     * @throws NullPointerException if item is null
     */
    public boolean contains(T item);
    
    /**
     * Displays all items in the bag.
     */
    public void displayItems();
    
    /**
     * Removes all items from the bag.
     */
    public void dump();
    
    /**
     * Transfers the specified item from this bag to the target bag.
     * 
     * @param targetBag the bag to transfer the item to
     * @param item the item to transfer
     * @return true if the item was successfully transferred, false otherwise
     * @throws NullPointerException if targetBag or item is null
     */
    public boolean transferTo(IBag<T> targetBag, T item);

    /**
     * Picks a random item from the bag.
     * 
     * @return a random item from the bag
     * @throws NoSuchElementException if the bag is empty
     */
    public T getRandomItem();

     /**
     * Returns the contents of the bag as a KeyValuePairArray.
     * KeyValuePairArray is a Map-like structure,
     * implemented from scratch since we are not allowed to use JCL.
     */
    public KeyValuePairArray getContentsMap();

    /**
     * Turns the bag into an array.
     * 
     * @return a T[] array
     */
    public T[] toArray();
}
