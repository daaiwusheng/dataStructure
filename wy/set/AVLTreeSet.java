package com.wy.set;

import com.wy.binaryTree.AVLTree;

public class AVLTreeSet<E> implements Set<E> {

	private AVLTree<E> tree = new AVLTree<>();
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return tree.isEmpty();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		tree.clear();
	}

	@Override
	public boolean contains(E element) {
		// TODO Auto-generated method stub
		return tree.contains(element);
	}

	@Override
	public void add(E element) {
		// TODO Auto-generated method stub
		tree.add(element);
	}

	@Override
	public void remove(E element) {
		// TODO Auto-generated method stub
		tree.deleteElement(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		// TODO Auto-generated method stub
		tree.inorderTraversal(new com.wy.binaryTree.BinaryTree.Visitor<E>() {
		
			public void visitElement(E element) {
				visitor.visit(element);
				
			}
		
		});
	}

}
