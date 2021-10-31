package com.wy.binaryTree;

import com.mj.printer.BinaryTrees;
import com.wy.binaryTree.BinaryTree.Node;
import com.wy.binaryTree.BinaryTree.Node.NodeChildType;

public class AVLTree<E> extends BalanceBinarySearchTree<E> {

	protected static class AVLNode<E> extends Node<E>{

		int height = 1;
		public AVLNode(E element) {
			super(element);
			
		}
		
		public void updateHeight() {
			int leftChildHeight = leftNode == null?0:((AVLNode<E>)leftNode).height;
			int rightChildHeight = rightNode == null?0:((AVLNode<E>)rightNode).height;
			
			height = Math.max(leftChildHeight, rightChildHeight) + 1;
		}
		
		public int calculateBalanceFactor() {
			int leftChildHeight = leftNode == null?0:((AVLNode<E>)leftNode).height;
			int rightChildHeight = rightNode == null?0:((AVLNode<E>)rightNode).height;
			
			return leftChildHeight - rightChildHeight;
		}
		
		
		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			
//			String parentString = parentNode != null? parentNode.element.toString():"null";
			
			stringBuilder.append(element);//.append("_P").append("["+parentString+"]").append("h["+height+"]");
			return stringBuilder.toString();
		}
		
	}
	
	@Override
	protected Node<E> creatNewNode(E element) {
		return new AVLNode(element);
	}
	
	@Override
	protected void afterReplaceByNewNode(Node<E> newNode, Node<E> oldNode) {
		//we only need to update this node's height, as its child nodes won't change anything
		((AVLNode<E>)newNode).updateHeight();
	}
	
	@Override
	protected void afterAddNewNode(Node<E> newNode) {
		
		AVLNode<E> avlNode = (AVLNode<E>)newNode;
		
		while ((avlNode = ((AVLNode<E>)avlNode.parentNode)) != null) {
			if (isBalanced(avlNode)) {
				avlNode.updateHeight();
			}
			else {
				//reBalance current node
//				reBalance(avlNode);
				reBalanceByCommonMethod(avlNode);
				break;
			}
				
		}
		
	}
	
	@Override
	protected void afterDelete(Node<E> deletedNode) {
		AVLNode<E> avlNode = (AVLNode<E>)deletedNode;
		
		while ((avlNode = ((AVLNode<E>)avlNode.parentNode)) != null) {
			if (isBalanced(avlNode)) {
				avlNode.updateHeight();
			}
			else {
				reBalanceByCommonMethod(avlNode);
			}
				
		}
	}
	
	private void reBalanceByCommonMethod(AVLNode<E> avlNotBanlancedNode) {
		AVLNode<E> grandNode = avlNotBanlancedNode;
		AVLNode<E> parentNode = getHigherChildNode(grandNode);
		AVLNode<E> sonNode = getHigherChildNode(parentNode);
		NodeChildType pareNodeChildType = parentNode.judgeChildType();
		NodeChildType sonNodeChildType = sonNode.judgeChildType();
		
		if (pareNodeChildType == NodeChildType.Left && sonNodeChildType == NodeChildType.Left) {
			//LL grand turns right
			rotateForBalance(
					grandNode, 
					(AVLNode<E>) sonNode.leftNode,sonNode, (AVLNode<E>)sonNode.rightNode, 
					parentNode, 
					(AVLNode<E>)parentNode.rightNode, grandNode, (AVLNode<E>)grandNode.rightNode);
		}
		else if (pareNodeChildType == NodeChildType.Left && sonNodeChildType == NodeChildType.Right) {
			//LR parent turns left, then grand turns right
			rotateForBalance(
					grandNode, 
					(AVLNode<E>)parentNode.leftNode, parentNode, (AVLNode<E>)sonNode.leftNode, 
					sonNode, 
					(AVLNode<E>)sonNode.rightNode, grandNode, (AVLNode<E>)grandNode.rightNode);
		}
		else if (pareNodeChildType == NodeChildType.Right && sonNodeChildType == NodeChildType.Right) {
			//RR grand turns left
			rotateForBalance(
					grandNode, 
					(AVLNode<E>)grandNode.leftNode, grandNode, (AVLNode<E>)parentNode.leftNode, 
					parentNode, 
					(AVLNode<E>)sonNode.leftNode, sonNode, (AVLNode<E>)sonNode.rightNode);
		}
		else {
			//RL parent turns right, then grand turns left
			rotateForBalance(
					grandNode, 
					(AVLNode<E>)grandNode.leftNode, grandNode,(AVLNode<E>)sonNode.leftNode, 
					sonNode, 
					(AVLNode<E>)sonNode.rightNode, parentNode, (AVLNode<E>)parentNode.rightNode);
		}
		
	}
	
	@Override
	protected void rotateForBalance(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e,
			Node<E> f, Node<E> g) {
		// TODO Auto-generated method stub
		super.rotateForBalance(r, a, b, c, d, e, f, g);
		((AVLNode<E>)b).updateHeight();
		((AVLNode<E>)f).updateHeight();
		((AVLNode<E>)d).updateHeight();
	}	
	private void reBalance(AVLNode<E> avlNotBanlancedNode) {
		AVLNode<E> grandNode = avlNotBanlancedNode;
		AVLNode<E> parentNode = getHigherChildNode(grandNode);
		AVLNode<E> sonNode = getHigherChildNode(parentNode);
		NodeChildType pareNodeChildType = parentNode.judgeChildType();
		NodeChildType sonNodeChildType = sonNode.judgeChildType();
		
		if (pareNodeChildType == NodeChildType.Left && sonNodeChildType == NodeChildType.Left) {
			//LL grand turns right
			rotateToRight(grandNode, parentNode);
			grandNode.updateHeight();
			parentNode.updateHeight();
		}
		else if (pareNodeChildType == NodeChildType.Left && sonNodeChildType == NodeChildType.Right) {
			//LR parent turns left, then grand turns right
			rotateToLeft(parentNode, sonNode);
			//grand turns right
			rotateToRight(grandNode, sonNode);
			
			parentNode.updateHeight();
			grandNode.updateHeight();
//			((AVLNode<E>)newNode).updateHeight();
		}
		else if (pareNodeChildType == NodeChildType.Right && sonNodeChildType == NodeChildType.Right) {
			//RR grand turns left
			rotateToLeft(grandNode, parentNode);
			grandNode.updateHeight();
			parentNode.updateHeight();
		}
		else {
			//RL parent turns right, then grand turns left
			rotateToRight(parentNode, sonNode);
			//grand turns left
			rotateToLeft(grandNode, sonNode);
			parentNode.updateHeight();
			grandNode.updateHeight();
//			((AVLNode<E>)newNode).updateHeight();
		}

	}
	
	private AVLNode<E> getHigherChildNode(AVLNode<E> parentNode) {
		int leftHeight = parentNode.leftNode!=null?((AVLNode<E>)parentNode.leftNode).height:0;
		int rightHeight = parentNode.rightNode!=null?((AVLNode<E>)parentNode.rightNode).height:0;
		if (leftHeight>rightHeight) {
			return ((AVLNode<E>)parentNode.leftNode);
		}
		else if (rightHeight>leftHeight) {
			return ((AVLNode<E>)parentNode.rightNode);
		}
		else {
			//equal
			
			NodeChildType parChildType = parentNode.judgeChildType();
			if (parChildType == NodeChildType.Left) {
				return ((AVLNode<E>)parentNode.leftNode);
			}
			else if (parChildType == NodeChildType.Right) {
				return ((AVLNode<E>)parentNode.rightNode);
			}
			else {
				//here means parentNode is rootNode, while its left and right has equal height.
				//then provide left default
				return ((AVLNode<E>)parentNode.leftNode);
			}
		}
	}
	
	private boolean isBalanced(AVLNode<E> avlNode) {
		int balanceFactor = avlNode.calculateBalanceFactor();
		return Math.abs(balanceFactor)<2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object string(Object node) {
		return ((Node<E>)node).toString();
	}
}


































