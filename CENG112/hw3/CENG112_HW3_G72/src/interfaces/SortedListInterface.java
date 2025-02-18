package interfaces;

import java.util.Iterator;

public interface SortedListInterface<T extends Comparable<? super T>> extends Iterable<T> {

    /**
     * Adds an element to the sorted list while maintaining the sorting order.
     *
     * @param element the element to be added
     */
    void add(T element);

    /**
     * Removes an element from the sorted list.
     *
     * @param element the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    boolean remove(T element);

    /**
     * Retrieves the element at the specified index in the sorted list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index, or null if the index is out of bounds
     */
    T get(int index);

    /**
     * Checks if the sorted list contains the specified element.
     *
     * @param element the element to search for
     * @return true if the element is found, false otherwise
     */
    boolean contains(T element);

    /**
     * Retrieves the number of elements in the sorted list.
     *
     * @return the number of elements in the sorted list
     */
    int size();

    /**
     * Checks if the sorted list is empty.
     *
     * @return true if the sorted list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Removes all elements from the sorted list.
     */
    void clear();

    /**
     * Returns an iterator over the elements in the sorted list.
     *
     * @return an iterator over the elements in the sorted list
     */
    Iterator<T> iterator();
}
