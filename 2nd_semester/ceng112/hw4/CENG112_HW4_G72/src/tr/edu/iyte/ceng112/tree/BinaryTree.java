package tr.edu.iyte.ceng112.tree;

import java.util.Iterator;

import tr.edu.iyte.ceng112.traversaliterator.InorderIterator;
import tr.edu.iyte.ceng112.traversaliterator.LevelOrderIterator;
import tr.edu.iyte.ceng112.traversaliterator.PostorderIterator;
import tr.edu.iyte.ceng112.traversaliterator.PreorderIterator;

public class BinaryTree<T> implements BinaryTreeInterface<T> {

	private BinaryNode<T> root;

	public BinaryTree() {
		root = null;
	}

	public BinaryTree(T rootData) {
		root = new BinaryNode<>(rootData);
	}

	public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		privateSetTree(rootData, leftTree, rightTree);
	}

	public BinaryNode<T> getRoot() {
		return root;
	}

	protected void setRoot(BinaryNode<T> root) {
		this.root = root;
	}

	@Override
	public void setTree(T rootData) {
		root = new BinaryNode<>(rootData);

	}

	@Override
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		privateSetTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);

	}

	@Override
	public T getRootData() throws EmptyTreeException {

		if (isEmpty())
			throw new EmptyTreeException();
		else
			return root.getData();
	}

	@Override
	public int getHeight() {

		return root.getHeight();
	}

	@Override
	public int getNumberOfNodes() {

		return root.getNumberOfNodes();
	}

	@Override
	public boolean isEmpty() {

		return root == null;
	}

	@Override
	public void clear() {
		root = null;

	}

	public void preorderTraverse() {
		preorderTraverse(root);
	}

	private void preorderTraverse(BinaryNode<T> node) {
		if (node != null) {
			System.out.print(node.getData() + " ");
			preorderTraverse(node.getLeftChild());
			preorderTraverse(node.getRightChild());
		}
	}

	public void inorderTraverse() {
		inorderTraverse(root);
	}

	private void inorderTraverse(BinaryNode<T> node) {
		if (node != null) {
			inorderTraverse(node.getLeftChild());
			System.out.print(node.getData() + " ");
			inorderTraverse(node.getRightChild());
		}
	}

	public void postorderTraverse() {
		postorderTraverse(root);
	}

	private void postorderTraverse(BinaryNode<T> node) {
		if (node != null) {
			postorderTraverse(node.getLeftChild());
			postorderTraverse(node.getRightChild());
			System.out.print(node.getData() + " ");
		}
	}
	

	private void privateSetTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		root = new BinaryNode<>(rootData);
		if ((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);
		if ((rightTree != null) && !rightTree.isEmpty()) {
			if (rightTree != leftTree)
				root.setRightChild(rightTree.root);
			else
				root.setRightChild(rightTree.root.copy());
		}

		if ((leftTree != null) && (leftTree != this))
			leftTree.clear();
		if ((rightTree != null) && (rightTree != this))
			rightTree.clear();
	}

	@Override
	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator<>(root);
	}

	@Override
	public Iterator<T> getInorderIterator() {
		return new InorderIterator<>(root);
	}

	@Override
	public Iterator<T> getPostorderIterator() {
		return new PostorderIterator<>(root);
	}

	@Override
	public Iterator<T> getLevelOrderIterator() {
		return new LevelOrderIterator<>(root);
	}

}
