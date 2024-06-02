package interfaces;

import java.util.Iterator;

/**
 * An interface representing a generic stack.
 *
 * @param <T> the type of elements in the stack
 */
public interface StackInterface<T> extends Iterable<T> {

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param element the element to be pushed
     */
    void push(T element);

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T pop();

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the element at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T peek();

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements in the stack
     */
    int size();

    /**
     * Checks whether the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in the stack.
     *
     * @return an iterator over the elements in the stack
     */
    @Override
    Iterator<T> iterator();
}
