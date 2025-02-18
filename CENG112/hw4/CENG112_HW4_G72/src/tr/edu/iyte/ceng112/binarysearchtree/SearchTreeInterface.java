package tr.edu.iyte.ceng112.binarysearchtree;

import java.util.Iterator;

import tr.edu.iyte.ceng112.tree.TreeInterface;

public interface SearchTreeInterface<T extends Comparable<? super T>> extends TreeInterface<T>{

	public boolean contains(T entry);
	public T getEntry(T entry);
	public T add(T newEntry);
	public T remove(T entry);
	public Iterator<T> getPreorderIterator();
	public Iterator<T> getInorderIterator();
	public Iterator<T> getPostorderIterator();
	public Iterator<T> getLevelOrderIterator();
}
