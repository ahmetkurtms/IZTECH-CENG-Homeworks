package tr.edu.iyte.ceng112.stack;

public interface StackInterface <T> {
	public void push(T newEntry);
	public T pop();
	public T peek();
	public boolean isEmpty();
	public void clear();
}
