package tr.edu.iyte.ceng112.tree;

import java.util.Iterator;

public interface TreeIteratorInterface<T> {
	
	
	public Iterator<T> getPreorderIterator();
	public Iterator<T> getInorderIterator();
	public Iterator<T> getPostorderIterator();
	public Iterator<T> getLevelOrderIterator();

}
