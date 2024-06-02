package implementations;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.ListInterface;

/**
 * The LinkedList implementation of the ListInterface.
 *
 * @param <T> the type of elements in the list
 */
public class LinkedList<T> implements ListInterface<T> {

    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public LinkedList() {
        head = null;
        size = 0;
    }

 
    @Override
    public void add(T element) {
        add(size, element);
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> newNode = new Node<>(element);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }
    
    @Override
    public boolean remove(int index) {
        if (index < 0 || index >= size) {
            return false;
        }

        if (index == 0) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        current.next = current.next.next;
        size--;
        return true;
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
            if (current == null) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
