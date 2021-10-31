package com.wy.set;

import com.wy.binaryTree.RedBlackTree;

public class RedBlackTreeSet<E> implements Set<E> {
	
	
	private RedBlackTree<E> tree = new RedBlackTree<>();

	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public void clear() {
		tree.clear();

	}

	@Override
	public boolean contains(E element) {
		
		return tree.contains(element);
	}

	@Override
	public void add(E element) {
		tree.add(element);
	}

	@Override
	public void remove(E element) {
		tree.deleteElement(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {	
		tree.inorderTraversal(new com.wy.binaryTree.BinaryTree.Visitor<E>() {
			
			public void visitElement(E element) {
				visitor.visit(element);
				
			}
			
		});
	}

}






















