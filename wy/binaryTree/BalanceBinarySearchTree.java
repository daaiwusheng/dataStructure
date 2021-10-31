package com.wy.binaryTree;

import java.util.Comparator;

import com.wy.binaryTree.BinaryTree.Node.NodeChildType;

public class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {

	public BalanceBinarySearchTree() {
		// TODO Auto-generated constructor stub
	}

	public BalanceBinarySearchTree(Comparator<E> comparator) {
		super(comparator);
		// TODO Auto-generated constructor stub
	}

	protected void rotateForBalance(
			Node<E> r,
			Node<E> a,Node<E> b,Node<E> c,
			Node<E> d,
			Node<E> e,Node<E> f,Node<E> g
			) {
		com.wy.binaryTree.BinaryTree.Node.NodeChildType rChildType = r.judgeChildType();
		if (rChildType == NodeChildType.Left) {
			r.parentNode.leftNode = d;
			d.parentNode = r.parentNode;
		}
		else if (rChildType == NodeChildType.Right) {
			r.parentNode.rightNode = d;
			d.parentNode = r.parentNode;
		}
		else {
			rootNode = d;
			d.parentNode = null;
		}
		
		b.leftNode = a;
		if (a != null) {
			a.parentNode = b;
		}
		b.rightNode = c;
		if (c != null) {
			c.parentNode = b;
		}
		
		
		f.leftNode = e;
		if (e != null) {
			e.parentNode = f;
		}
		f.rightNode = g;
		if (g != null) {
			g.parentNode = f;
		}
		
		
		d.leftNode = b;
		b.parentNode = d;
		d.rightNode = f;
		f.parentNode = d;
		
	}

	protected void rotateToLeft(Node<E> upperNode, Node<E> lowerNode) {
		
		NodeChildType upperNodeChildType = upperNode.judgeChildType();
		
		if (upperNodeChildType == NodeChildType.Left) {
			upperNode.parentNode.leftNode = lowerNode;
			lowerNode.parentNode = upperNode.parentNode;
			
		}
		else if (upperNodeChildType == NodeChildType.Right) {
			upperNode.parentNode.rightNode = lowerNode;
			lowerNode.parentNode = upperNode.parentNode;
		}
		else {
			//upperNode is the rootNode 
			rootNode = lowerNode;
			lowerNode.parentNode = null;
		}
		
		upperNode.rightNode = lowerNode.leftNode;
		if (upperNode.rightNode!=null) {
			upperNode.rightNode.parentNode = upperNode;
		}
	
		lowerNode.leftNode = upperNode;
		upperNode.parentNode = lowerNode;

	}
	
	protected void rotateToRight(Node<E> upperNode, Node<E> lowerNode) {
		
		NodeChildType upperChildType = upperNode.judgeChildType();
		if (upperChildType == NodeChildType.Left) {
			upperNode.parentNode.leftNode = lowerNode;
			lowerNode.parentNode = upperNode.parentNode;
		}
		else if (upperChildType == NodeChildType.Right) {
			upperNode.parentNode.rightNode = lowerNode;
			lowerNode.parentNode = upperNode.parentNode;
		}
		else {
			// upperNode is the rootNode
			rootNode = lowerNode;
			lowerNode.parentNode = null;
		}
		
		upperNode.leftNode = lowerNode.rightNode;
		if (upperNode.leftNode!=null) {
			upperNode.leftNode.parentNode = upperNode;
		}
		
		lowerNode.rightNode = upperNode;
		upperNode.parentNode = lowerNode;	
	}
}
