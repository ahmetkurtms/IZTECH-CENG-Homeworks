package interfaces;

import java.util.Iterator;

/**
 * Interface for a priority queue.
 *
 * @param <T> the type of elements in the priority queue
 */
public interface PriorityQueueInterface<T> extends Iterable<T> {
    
    /**
     * Inserts an element into the priority queue.
     *
     * @param element the element to insert
     */
    void offer(T element);
    
    /**
     * Retrieves and removes the highest-priority element from the priority queue.
     *
     * @return the highest-priority element, or null if the priority queue is empty
     */
    T poll();
    
    /**
     * Retrieves, but does not remove, the highest-priority element from the priority queue.
     *
     * @return the highest-priority element, or null if the priority queue is empty
     */
    T peek();
    
    /**
     * Returns the number of elements in the priority queue.
     *
     * @return the number of elements in the priority queue
     */
    int size();
    
    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in the sorted list.
     *
     * @return an iterator over the elements in the sorted list
     */
    Iterator<T> iterator();
}
