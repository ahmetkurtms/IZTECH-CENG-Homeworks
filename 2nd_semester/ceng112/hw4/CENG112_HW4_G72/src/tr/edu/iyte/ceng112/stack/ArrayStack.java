package tr.edu.iyte.ceng112.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<T> implements StackInterface<T> {
	private T[] stack;
	private int topIndex;
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;

	public ArrayStack() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayStack(int initialCapacity) {
		checkCapacity(initialCapacity);
		@SuppressWarnings({ "unchecked" })
		T[] tempStack = (T[]) new Object[initialCapacity + 1];
		stack = tempStack;
		initialized = true;
		topIndex = -1;

	}

	@Override
	public void push(T newEntry) {
		checkInitialization();
		ensureCapacity();
		stack[topIndex + 1] = newEntry;
		topIndex++;

	}

	@Override
	public T pop() {
		checkInitialization();
		if (isEmpty())
			throw new EmptyStackException();
		else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		}
	}

	@Override
	public T peek() {
		checkInitialization();
		if (isEmpty())
			throw new EmptyStackException();
		else
			return stack[topIndex];
	}

	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	@Override
	public void clear() {

		for (int i = 0; i < topIndex; i++) {
			stack[i] = null;
		}
		topIndex = 0;
	}

	private void checkCapacity(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY) {
			throw new IllegalStateException("max capacity exceed!");
		}

	}

	private void ensureCapacity() {

		if (topIndex == stack.length - 1) {
			int newLength = 2 * stack.length;
			checkCapacity(newLength);
			stack = Arrays.copyOf(stack, newLength);
		}
	}

	private void checkInitialization() {
		if (!initialized)
			throw new SecurityException("Queue is not initialized properly");

	}

}
