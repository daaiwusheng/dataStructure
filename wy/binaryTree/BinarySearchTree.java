package com.wy.binaryTree;

import java.util.ArrayList;
import java.util.Comparator;


@SuppressWarnings("unchecked")

public class BinarySearchTree<E> extends BinaryTree<E> {

	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	private compareTwoNode compare(Node<E> nodeInTree, Node<E> newNode) {
		return compareTwoElement(nodeInTree.element,newNode.element);
	}
	@SuppressWarnings("unchecked")
	private compareTwoNode compareTwoElement(E elementInTree, E element) {
		int compareResult;
		if (comparator != null) {
			compareResult = comparator.compare(elementInTree, element);
		}
		else {
			compareResult = ((Comparable<E>)elementInTree).compareTo(element);
		}
		
		if (compareResult>0) {
			return compareTwoNode.Left_ParentIsBigger;
		}
		else if (compareResult<0) {
			return compareTwoNode.Right_ParentIsSmaller;
		}
		else {
			return compareTwoNode.Equal;
		}

	}
	
	
	private	enum compareTwoNode
	{
		Left_ParentIsBigger,Right_ParentIsSmaller,Equal;
		
	}
	
	public void add(E element) {
		
		Node<E> newNode = creatNewNode(element);
		
		if (isEmpty()) {
			rootNode = newNode;
		}
		else {
			Node<E> targetNode = rootNode;
			Node<E> parentNode = rootNode;
			
			compareTwoNode directionForAdd = compareTwoNode.Left_ParentIsBigger;
			while (targetNode != null) {
				
				parentNode = targetNode;
				directionForAdd = compare(targetNode, newNode);
				if (directionForAdd == compareTwoNode.Left_ParentIsBigger) {
					//targetNode is bigger
					targetNode = targetNode.leftNode;
				}
				else if (directionForAdd == compareTwoNode.Right_ParentIsSmaller) {
					//targetNode is smaller
					targetNode = targetNode.rightNode;
				}
				else {
					newNode.leftNode = targetNode.leftNode;
					newNode.rightNode = targetNode.rightNode;
					newNode.parentNode = targetNode.parentNode;
					targetNode.element = newNode.element;
					afterReplaceByNewNode(newNode,targetNode);
					return;
				}
		
			}
			
			if (directionForAdd == compareTwoNode.Left_ParentIsBigger) {
				parentNode.leftNode = newNode;
				newNode.parentNode = parentNode;
			}
			else {
				parentNode.rightNode = newNode;
				newNode.parentNode = parentNode;
			}
		}
		size ++;
		afterAddNewNode(newNode);
	}
	
	protected Node<E> creatNewNode(E element) {
		return new Node<E>(element);
	}
	
	protected void afterReplaceByNewNode(Node<E> node,Node<E> oldNode) {
		// implement by subClass
	}
	
	protected void afterAddNewNode(Node<E> node) {
		// implement by subClass
	}
	
	@Override
	protected Node<E> node(E element) {
		
		Node<E> currentNode = rootNode;
		compareTwoNode result = compareTwoNode.Left_ParentIsBigger;
		
		while (currentNode != null) {
			result = compareTwoElement(currentNode.element, element);
			if (result == compareTwoNode.Left_ParentIsBigger) {
				currentNode = currentNode.leftNode;
			}
			else if (result == compareTwoNode.Right_ParentIsSmaller) {
				currentNode = currentNode.rightNode;
			}
			else {
				return currentNode;
			}
			
		}
		return null;
		
	}
	
	

	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		toString(stringBuilder,rootNode,"");
		
		return stringBuilder.toString();
	}
	
	private void toString(StringBuilder stringBuilder,Node<E> rootNode,String prefixString) {
		
		if (rootNode == null) {
			return;
		}
		
		stringBuilder.append(prefixString).append(rootNode.element).append("\n");
	
		prefixString = prefixString + "--";
		
		toString(stringBuilder,rootNode.leftNode, prefixString+"L-");
		
		toString(stringBuilder,rootNode.rightNode, prefixString+"R-");
		
	}
	
		
	public  BinarySearchTree<E> recoverBinaryTreeViaPreorderAndInorderList(
			ArrayList<E> preorderArrayList,
			ArrayList<E> inordArrayList) {
		
		BinarySearchTree<E> recoverTree = new BinarySearchTree<>();
				
		recoverTree.rootNode = recoverTree(preorderArrayList, inordArrayList);
		
		return recoverTree;
		
	}

	public void deleteElement(E element) {
		
		Node<E> targetNode = node(element);
		
		if (targetNode==null) {
			return;
		}
		else {
			size --;
		}
		
		if (judgeNodeDegree(targetNode) == 2) {
			//一定有前驱和后继节点,并且前驱和后继节点的度一定是1或者0
			
			Node<E> successorNode = findeSucNode(targetNode);
			targetNode.element = successorNode.element;
			targetNode = successorNode;
		}
		//targetNode is the real one that has been removed
		if (targetNode.rightNode != null||targetNode.leftNode != null) {
			Node<E> nextNode = (targetNode.rightNode!=null)?targetNode.rightNode:targetNode.leftNode;
			if (targetNode.parentNode == null) {
				rootNode = nextNode;
				nextNode.parentNode = null;
			}
			else if (targetNode == targetNode.parentNode.leftNode) {
				targetNode.parentNode.leftNode = nextNode;
				nextNode.parentNode = targetNode.parentNode;
			}
			else {
				targetNode.parentNode.rightNode = nextNode;
				nextNode.parentNode = targetNode.parentNode;
			}
			
			if (!isEmpty()) {
				afterDelete(nextNode);
			}
		}
		else {
			if (targetNode.parentNode == null) {
				targetNode = null;
				rootNode = null;
			}
			else if (targetNode == targetNode.parentNode.leftNode) {
				targetNode.parentNode.leftNode = null;
			}
			else {
				targetNode.parentNode.rightNode = null;
			}
			if (!isEmpty()) {
				afterDelete(targetNode);
			}
		}
		
		
	}
	protected void afterDelete(Node<E> deletedNode) {
		//can be implemented by sub classes
	}
}














