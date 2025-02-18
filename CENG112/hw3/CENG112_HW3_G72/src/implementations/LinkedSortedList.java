package implementations;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.SortedListInterface;

/**
 * The LinkedSortedList implementation of the SortedListInterface.
 * This implementation stores elements in descending order.
 * 
 * @param <T> the type of elements in the list
 */
public class LinkedSortedList<T extends Comparable<? super T>> implements SortedListInterface<T> {

    private Node<T> head;
    private Comparator<T> comparator;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public LinkedSortedList() {
        head = null;
        comparator = null;
        size = 0;
    }

    public LinkedSortedList(Comparator<T> comparator) {
        head = null;
        this.comparator = comparator;
        size = 0;
    }

    @Override
    public void add(T element) {
        
        if (comparator == null) {
            addComparable(element);
        } else {
            addComparator(element);
        }
    }

    // The add method is slightly different from the one in the book, because
    // we are adding elements in descending order.
    private void addComparable(T element) {
        
        Node<T> newNode = new Node<>(element);

        if (head == null || element.compareTo(head.data) >= 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null && element.compareTo(current.next.data) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    // The add method is slightly different from the one in the book, because
    // we are adding elements in descending order.
    private void addComparator(T element) {
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
    public boolean remove(T element) {
        if (head == null) {
            return false;
        }

        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
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
    public void clear() {
        head = null;
        size = 0;
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
