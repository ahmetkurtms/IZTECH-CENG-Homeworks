package tr.edu.iyte.ceng112.traversaliterator;

import java.util.Iterator;

import tr.edu.iyte.ceng112.stack.ArrayStack;
import tr.edu.iyte.ceng112.stack.StackInterface;
import tr.edu.iyte.ceng112.tree.BinaryNode;


public class PreorderIterator<T> implements Iterator<T> {
    
    private StackInterface<BinaryNode<T>> nodeStack;

    public PreorderIterator(BinaryNode<T> root) {
        nodeStack = new ArrayStack<>();
        if (root != null) {
            nodeStack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    @Override
    public T next() {
        // Since hasNext() is called before next(), it is redundant to check if the stack is empty or not.
        BinaryNode<T> nextNode = nodeStack.pop();
        T data = nextNode.getData();

        if (nextNode.getRightChild() != null) {
            nodeStack.push(nextNode.getRightChild());
        }
        if (nextNode.getLeftChild() != null) {
            nodeStack.push(nextNode.getLeftChild());
        }

        return data;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
