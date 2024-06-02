package tr.edu.iyte.ceng112.binarysearchtree;

import tr.edu.iyte.ceng112.tree.BinaryNode;
import tr.edu.iyte.ceng112.tree.BinaryTree;
import tr.edu.iyte.ceng112.tree.BinaryTreeInterface;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> implements SearchTreeInterface<T> {

	public BinarySearchTree() {
		super();
	}

	public BinarySearchTree(T rootData) {
		super();
		setRoot(new BinaryNode<T>(rootData));
	}

	@Override
	public void setTree(T rootData) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean contains(T entry) {
		return getEntry(entry) != null;
	}

	@Override
	public T getEntry(T entry) {
		return findEntry(getRoot(), entry);
	}

	private T findEntry(BinaryNode<T> rootNode, T entry) {
		T result = null;
		if (rootNode != null) {
			T rootEntry = rootNode.getData();
			if (entry.equals(rootEntry))
				result = rootEntry;
			else if (entry.compareTo(rootEntry) < 0)
				result = findEntry(rootNode.getLeftChild(), entry);
			else
				result = findEntry(rootNode.getRightChild(), entry);
		} // end if
		return result;
	} // end findEntry

	@Override
	public T add(T newEntry) {
		T result = null;
		if (isEmpty())
			setRoot(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRoot(), newEntry);
		return result;
	}

	// recursive addEntry
	private T addEntry(BinaryNode<T> rootNode, T newEntry) {
		assert rootNode != null;
		T result = null;
		int comparison = newEntry.compareTo(rootNode.getData());
		if (comparison == 0) {
			result = rootNode.getData();
			rootNode.setData(newEntry);
		} else if (comparison < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), newEntry);
			else
				rootNode.setLeftChild(new BinaryNode<>(newEntry));
		} else {
			assert comparison > 0;
			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), newEntry);
			else
				rootNode.setRightChild(new BinaryNode<>(newEntry));
		} // end if
		return result;
	} // end addEntry


	@Override
	public T remove(T entry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRoot(), entry, oldEntry);
		setRoot(newRoot);
		return oldEntry.getData();
	}

	// Removes an entry from the tree rooted at a given node.
	// rootNode is a reference to the root of a tree.
	// entry is the object to be removed.
	// oldEntry is an object whose data field is null.
	// Returns the root node of the resulting tree; if entry matches
	// an entry in the tree, oldEntry’s data field is the entry
	// that was removed from the tree; otherwise it is null.
	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry, ReturnObject oldEntry) {

		if (rootNode != null) {
			T rootData = rootNode.getData();
			int comparison = entry.compareTo(rootData);
			if (comparison == 0) { // entry == root entry{
				oldEntry.setData(rootData);
				rootNode = removeFromRoot(rootNode);
			} else if (comparison < 0) // entry < root entry
			{
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, entry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
			} else // entry > root entry
			{
				BinaryNode<T> rightChild = rootNode.getRightChild();
				rootNode.setRightChild(removeEntry(rightChild, entry, oldEntry));
			} // end if
		} // end if
		return rootNode;
	}

	// Given the root of a subtree, removeFromRoot returns the root of the subtree
	// after a node is removed.
	// Removes the entry in a given root node of a subtree.
	// rootNode is the root node of the subtree.
	// Returns the root node of the revised subtree.
	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: rootNode has two children
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find node with largest entry in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

			// Replace entry in root
			rootNode.setData(largestNode.getData());
			// Remove node with largest entry in left subtree
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		} // end if
			// Case 2: rootNode has at most one child
		else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();
		// Assertion: If rootNode was a leaf, it is now null
		return rootNode;
	}

	// Finds the node containing the largest entry in a given tree.
	// rootNode is the root node of the tree.
	// Returns the node containing the largest entry in the tree.
	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = findLargest(rootNode.getRightChild());
		return rootNode;
	}

	// Removes the node containing the largest entry in a given tree.
	// rootNode is the root node of the tree.
	// Returns the root node of the revised tree.
	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
		} else
			rootNode = rootNode.getLeftChild();
		return rootNode;
	} // end removeLargest

	private class ReturnObject {
		private T data;

		public ReturnObject(T data) {
			setData(data);
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}
	}

}
