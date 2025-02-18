package tr.edu.iyte.ceng112.traversaliterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import tr.edu.iyte.ceng112.queue.ArrayQueue;
import tr.edu.iyte.ceng112.queue.EmptyQueueException;
import tr.edu.iyte.ceng112.queue.QueueInterface;
import tr.edu.iyte.ceng112.tree.BinaryNode;


public class LevelOrderIterator<T> implements Iterator<T> {
    
    private QueueInterface<BinaryNode<T>> nodeQueue;

    public LevelOrderIterator(BinaryNode<T> root) {
        nodeQueue = new ArrayQueue<>();
        if (root != null) {
            nodeQueue.enqueue(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !nodeQueue.isEmpty();
    }

    // We used a queue to implement level-order traversal since it is a FIFO data structure and it was mentioned in the lecture.
    @Override
    public T next() {
        BinaryNode<T> nextNode;
        // Since hasNext() is called before next(), we know that the queue is not empty.
        // Therefore we can handle EmptyQueueException with a default try-catch block.
        try {
            nextNode = nodeQueue.dequeue();
            if (nextNode.getLeftChild() != null) {
                nodeQueue.enqueue(nextNode.getLeftChild());
            }
            if (nextNode.getRightChild() != null) {
                nodeQueue.enqueue(nextNode.getRightChild());
            }
            return nextNode.getData();
        } catch (EmptyQueueException e) {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
