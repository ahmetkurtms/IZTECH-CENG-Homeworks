package tr.edu.iyte.ceng112.queue;

public interface QueueInterface <T> {
	public void enqueue (T entry);
	public T dequeue() throws EmptyQueueException;
	public T getFront() throws EmptyQueueException;
	public boolean isEmpty();
	public void clear() throws EmptyQueueException;
}
