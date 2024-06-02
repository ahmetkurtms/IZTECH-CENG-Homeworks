package tr.edu.iyte.ceng112.queue;

public class ArrayQueue <T> implements QueueInterface<T> {
	private T [] queue;
	private int frontIndex;
	

	public int getFrontIndex() {
		return frontIndex;
	}

	private int backIndex;
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY=50;
	private static final int MAX_CAPACITY=10000;
	public ArrayQueue() {
		this(DEFAULT_CAPACITY);
	}
	public ArrayQueue(int initialCapacity) {
		checkCapacity(initialCapacity);
		@SuppressWarnings({ "unchecked" })
		T[]tempQueue=(T[])new Object[initialCapacity+1];
		queue=tempQueue;
		initialized=true;
		frontIndex=0;
		backIndex=-1;
	}
	private void checkCapacity(int initialCapacity) {
		if(initialCapacity > MAX_CAPACITY) {
			throw new IllegalStateException("max capacity exceed!");
		}
		
	}
	@Override
	public void enqueue(T entry) {
		checkInitialization();
		ensureCapacity();
		backIndex=(backIndex+1)%queue.length;
		queue[backIndex]=entry;
		
	}

	private void ensureCapacity() {
		if(frontIndex==((backIndex+2) % queue.length)) {
			T[]oldQueue=queue;
			int oldSize=oldQueue.length;
			int newSize=2 * oldSize;
			checkCapacity(newSize);
			@SuppressWarnings("unchecked")
			T[]tempQueue=(T[])new Object[newSize];
			queue=tempQueue;
			for(int index=0; index<oldSize-1;index++) {
				queue[index]=oldQueue[frontIndex];
				frontIndex=(frontIndex+1)%oldSize;
			}
			frontIndex=0;
			backIndex=oldSize-2;
		}
		
	}
	private void checkInitialization() {
		if(!initialized)
			throw new SecurityException("Queue is not initialized properly");
		
	}
	@Override
	public T dequeue() throws EmptyQueueException {
		checkInitialization();
		if(isEmpty())
			throw new EmptyQueueException();
		else {
			T front=queue[frontIndex];
			queue[frontIndex]=null;
			frontIndex = (frontIndex + 1) % queue.length;
			return front;
		}
	}
	public void addFront(T entry) {
	    if ( frontIndex == 0 )
	    	frontIndex = queue.length - 1;
	    else 
	    	frontIndex = ( frontIndex - 1 ) % queue.length;

	    queue [frontIndex] = entry;
		
	}

	@Override
	public T getFront() throws EmptyQueueException {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else 
			return queue[frontIndex];
	}

	@Override
	public boolean isEmpty() {
		
		return frontIndex==((backIndex+1)%queue.length);
	}

	@Override
	public void clear() throws EmptyQueueException {
		while(!isEmpty())
			dequeue();
		
	}

}
