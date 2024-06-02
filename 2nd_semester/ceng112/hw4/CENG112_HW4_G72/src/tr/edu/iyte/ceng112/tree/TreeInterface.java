package tr.edu.iyte.ceng112.tree;

public interface TreeInterface<T> {
	
	public T getRootData() throws EmptyTreeException;
	public int getHeight();
	public int getNumberOfNodes();
	public boolean isEmpty();
	public void clear();

}
