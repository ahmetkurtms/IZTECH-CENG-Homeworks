package tr.edu.iyte.ceng112.tree;

public interface BinaryTreeInterface<T> extends TreeInterface<T>, TreeIteratorInterface<T> {

	public void setTree(T rootData);

	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree);

	

}
