package tr.edu.iyte.ceng112.traversaliterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import tr.edu.iyte.ceng112.stack.ArrayStack;
import tr.edu.iyte.ceng112.stack.StackInterface;
import tr.edu.iyte.ceng112.tree.BinaryNode;

public class InorderIterator<T> implements Iterator<T> {

	private StackInterface<BinaryNode<T>> nodeStack;
	private BinaryNode<T> currentNode;


	public InorderIterator(BinaryNode<T> root) {
		nodeStack = new ArrayStack<>();
		currentNode = (BinaryNode<T>) root;
	}

	@Override
	public boolean hasNext() {

		return !nodeStack.isEmpty() || (currentNode != null);
	}

	@Override
	public T next() {

		BinaryNode<T> nextNode = null;

		while (currentNode != null) {
			nodeStack.push(currentNode);
			currentNode = currentNode.getLeftChild();
		}

		if (!nodeStack.isEmpty()) {
			nextNode = nodeStack.pop();
			assert nextNode != null;
			currentNode = nextNode.getRightChild();
		} else
			throw new NoSuchElementException();

		return nextNode.getData();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
