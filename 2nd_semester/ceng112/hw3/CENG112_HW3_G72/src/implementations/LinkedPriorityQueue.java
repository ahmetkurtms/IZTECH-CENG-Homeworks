package implementations;

import interfaces.PriorityQueueInterface;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The LinkedPriorityQueue implementation of the PriorityQueueInterface.
 * This implementation stores elements in descending order.
 *
 * @param <T> the type of elements in the priority queue
 */
public class LinkedPriorityQueue<T> implements PriorityQueueInterface<T> {

    private Node<T> head;
    private Comparator<T> comparator;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedPriorityQueue(Comparator<T> comparator) {
        this.head = null;
        this.comparator = comparator;
        this.size = 0;
    }

    @Override
    public void offer(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty() || comparator.compare(element, head.data) >= 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null && comparator.compare(element, current.next.data) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T highestPriority = head.data;
        head = head.next;
        size--;

        return highestPriority;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return head.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(head);
    }

    private static class ListIterator<T> implements Iterator<T> {

        private Node<T> current;

        ListIterator(Node<T> head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list.");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
