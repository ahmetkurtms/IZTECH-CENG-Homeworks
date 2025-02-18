package tr.edu.iyte.ceng112.traversaliterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import tr.edu.iyte.ceng112.stack.ArrayStack;
import tr.edu.iyte.ceng112.stack.StackInterface;
import tr.edu.iyte.ceng112.tree.BinaryNode;


public class PostorderIterator<T> implements Iterator<T> {
    
    private StackInterface<BinaryNode<T>> nodeStack;
    private BinaryNode<T> currentNode;
    private BinaryNode<T> lastNodeVisited;

    public PostorderIterator(BinaryNode<T> root) {
        nodeStack = new ArrayStack<>();
        currentNode = (BinaryNode<T>) root;
        lastNodeVisited = null; // It was easier to implement Post Order Traversal with holding the last visited node.
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty() || (currentNode != null);
    }

    @Override
    public T next() {
        BinaryNode<T> nextNode = null;

        while (!nodeStack.isEmpty() || currentNode != null) {
            if (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            } else {
                BinaryNode<T> peekNode = nodeStack.peek();
                if (peekNode.getRightChild() != null && peekNode.getRightChild() != lastNodeVisited) {
                    currentNode = peekNode.getRightChild();
                } else {
                    nextNode = nodeStack.pop();
                    lastNodeVisited = nextNode;
                    return nextNode.getData();
                }
            }
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
