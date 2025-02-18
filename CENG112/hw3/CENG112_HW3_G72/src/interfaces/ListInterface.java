package interfaces;

import java.util.Iterator;

/**
 * The ListInterface represents a generic list of elements.
 *
 * @param <T> the type of elements in the list
 */
public interface ListInterface<T> extends Iterable<T> {

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to be added
     */
    void add(T element);

    /**
     * Adds an element at the specified index in the list.
     *
     * @param index   the index at which the element should be added
     * @param element the element to be added
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    void add(int index, T element);
    
    /**
     * Removes an element from the list by its index.
     *
     * @param index of the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    boolean remove(int index);

    /**
     * Removes an element from the list.
     *
     * @param element the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    boolean remove(T element);

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index, or null if the index is out of bounds
     */
    T get(int index);

    /**
     * Checks if the list contains the specified element.
     *
     * @param element the element to search for
     * @return true if the element is found, false otherwise
     */
    boolean contains(T element);

    /**
     * Retrieves the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    int size();

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Removes all elements from the list.
     */
    void clear();

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    @Override
    Iterator<T> iterator();
}
