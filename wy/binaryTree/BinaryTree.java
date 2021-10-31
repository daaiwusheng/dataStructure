package com.wy.binaryTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.mj.printer.BinaryTreeInfo;
import com.wy.tree.printerTool.WYBinaryTreeInfo;

public class BinaryTree<E> implements WYBinaryTreeInfo,BinaryTreeInfo {
	protected int size;
	protected Node<E> rootNode;
	protected Comparator<E> comparator;
	
	
	protected static class Node<E>{
		E element;
		Node<E> leftNode;
		Node<E> rightNode;
		Node<E> parentNode;
			
		public Node(E element) {
			this.element = element;
		}
		public static enum NodeChildType{
			Left,Right,Root;
		}
		
		public NodeChildType judgeChildType() {
			if (parentNode == null) {
				return NodeChildType.Root;
			}
			else if (this == parentNode.leftNode) {
				return NodeChildType.Left;
			} 
			else {
				return NodeChildType.Right;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(element.toString());//+"_");
//			if (parentNode!=null) {
//				stringBuilder.append(parentNode.element);
//			}
//			else {
//				stringBuilder.append("null");
//			}
			return stringBuilder.toString();
			
		}
	}
	
	
	
	public static abstract class Visitor<E>{
		
		public boolean isStop;
		
		public void visitElement(E element) {}
		
	}
	
	public void clear() {
		rootNode = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void preorderTraversal(Visitor<E> visitor) {
		preorder(rootNode,visitor);
	}
	
	protected void preorder(Node<E> rootNode,Visitor<E> visitor) {
		
		if (rootNode == null||visitor.isStop) {
			return;
		}
		
		visitor.visitElement(rootNode.element);
		
		preorder(rootNode.leftNode,visitor);
		
		preorder(rootNode.rightNode,visitor);
		
	}
	
	public void preorderTraversalNotByRecursion() {
		
		Node<E> currentNode = rootNode;
		
		Stack<Node<E>> stack = new Stack<>();
		
		stack.push(currentNode);
		
		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			System.out.println(currentNode);
			
			if (currentNode.rightNode != null) {
				stack.push(currentNode.rightNode);
			}
			
			if (currentNode.leftNode != null) {
				stack.push(currentNode.leftNode);
			}
			
		}
		
	}
	
	public void inorderTraversalNotByRecursion() {
		
		Node<E> currentNode = rootNode;
		
		Stack<Node<E>> stack = new Stack<>();
		
		while (true) {
			
			if (currentNode != null) {
				stack.push(currentNode);
				currentNode = currentNode.leftNode;
			}
			else if (stack.isEmpty()) {
				break;
			}
			else {
				currentNode = stack.pop();
				System.out.println(currentNode);
				currentNode = currentNode.rightNode;
			}
			
		}		
	
	}

	public void postorderTraversalNotByRecursion() {
		
		Node<E> currentNode = rootNode;
		
		Stack<Node<E>> stack = new Stack<>();
		
		Node<E> previousNode = null;
		
		stack.push(currentNode);
		
		while (!stack.isEmpty()) {
			
			currentNode = stack.peek();
			
			if ((currentNode.leftNode == null && currentNode.rightNode == null)
				||(previousNode != null && previousNode.parentNode == currentNode)
					) {
				
				previousNode = stack.pop();
				
				System.out.println(previousNode);
			}
			else {
				if (currentNode.rightNode != null) {
					stack.push(currentNode.rightNode);	
				}
				
				if (currentNode.leftNode != null) {
					stack.push(currentNode.leftNode);	
				}
				
			}
			
		}		
	
	}
	
	public void inorderTraversal(Visitor<E> visitor) {
		inorder(rootNode, visitor);
	}
	
	protected void inorder(Node<E> rootNode, Visitor<E> visitor) {
		
		if (rootNode == null|| visitor.isStop) {
			return;
		}
		inorder(rootNode.leftNode,visitor);
		
		if (visitor.isStop) {
			return;
		}
		
		visitor.visitElement(rootNode.element);
		
		inorder(rootNode.rightNode,visitor);
		
	}
	
	
		
	public void postorderTraversal(Visitor<E> visitor) {
		postorder(rootNode,visitor);
	}
	
	protected void postorder(Node<E> rootNode,Visitor<E> visitor) {
		
		if (rootNode == null||visitor.isStop) {
			return;
		}
		postorder(rootNode.leftNode,visitor);
		postorder(rootNode.rightNode,visitor);
		
		if (visitor.isStop) {
			return;
		}
		
		visitor.visitElement(rootNode.element);
	}
	
	public void levelOrderTraversal(Visitor<E> visitor) {
	
		levelOrder(rootNode,visitor);
	}
	
	protected void levelOrder(Node<E> rootNode,Visitor<E> visitor) {
		
		if (rootNode == null||visitor.isStop) {
			return;
		}
		
		ArrayList<Node<E>> arrayList = new ArrayList<>();
		arrayList.add(rootNode);
		ArrayList<Node<E>> arrayListNextLevel = new ArrayList<>();
		
		int countLevel = 0;
		
		while (arrayList.size()>0) {

			System.out.print("level:="+ countLevel+":=  ");
			countLevel ++;
			for (int i = 0; i < arrayList.size(); i++) {
				Node<E> nodeInSameLevel = arrayList.get(i);
				
				visitor.visitElement(nodeInSameLevel.element);
				
				if (visitor.isStop) {
					return;
				}
				
				System.out.print("  ");
				if (nodeInSameLevel.leftNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.leftNode);
				}
				if (nodeInSameLevel.rightNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.rightNode);
				}
				
			}
			
			System.out.print("\n");
			
			arrayList.clear();
			arrayList.addAll(arrayListNextLevel);
			arrayListNextLevel.clear();
		}
		
		
	}
	public void levelOrderTraversalByQueue(Visitor<E> visitor) {
		
		levelOrderByQueue(rootNode, visitor);
	}
	
	protected void levelOrderByQueue(Node<E> rootNode,Visitor<E> visitor) {
		
		if (rootNode == null||visitor.isStop) {
			return;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		
		queue.offer(rootNode);
		
		while (queue.size()>0) {
			Node<E> headNode = queue.poll();
			visitor.visitElement(headNode.element);
			if (visitor.isStop) {
				return;
			}
			if (headNode.leftNode!=null) {
				queue.offer(headNode.leftNode);
			}
			
			if (headNode.rightNode!=null) {
				queue.offer(headNode.rightNode);
			}
		}
		
	}

	public void print() {
		
		preorderPrint(rootNode, "");
		
	}
	
	protected void preorderPrint(Node<E> rootNode,String prefixString) {
		if (rootNode == null) {
			return;
		}
		
		System.out.println(prefixString+rootNode.element);
		
		prefixString = prefixString + "--";
		
		preorderPrint(rootNode.leftNode,prefixString+"L-");
		
		preorderPrint(rootNode.rightNode,prefixString+"R-");
	}
	
	public int calculateHeight() {
		
//		return calculateHeightByRecursion(rootNode);
//		return calculateHeightByTraverse(rootNode);
		return calculateHeightByQueueTraverse(rootNode);
	}
	
	protected int calculateHeightByRecursion(Node<E> rootNode) {
		
		if (rootNode == null) {
			return 0;
		}
		
		return Math.max(calculateHeightByRecursion(rootNode.leftNode), calculateHeightByRecursion(rootNode.rightNode)) + 1;
		
	}
	
	protected int calculateHeightByTraverse(Node<E> rootNode) {
		
		if (rootNode == null) {
			return 0;
		}
		
		ArrayList<Node<E>> arrayList = new ArrayList<>();
		arrayList.add(rootNode);
		ArrayList<Node<E>> arrayListNextLevel = new ArrayList<>();
		
		int countLevel = 0;
		
		while (arrayList.size()>0) {
			countLevel ++;
			for (int i = 0; i < arrayList.size(); i++) {
				Node<E> nodeInSameLevel = arrayList.get(i);
				if (nodeInSameLevel.leftNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.leftNode);
				}
				if (nodeInSameLevel.rightNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.rightNode);
				}
				
			}			
			arrayList.clear();
			arrayList.addAll(arrayListNextLevel);
			arrayListNextLevel.clear();
		}
		
		return countLevel;
	}
	
	protected int calculateHeightByQueueTraverse(Node<E> rootNode) {
		
		if (rootNode == null) {
			return 0;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(rootNode);
		
		
		int countLevel = 0;
		int currentSize = queue.size();
		
		
		while (queue.size()>0) {
			
			Node<E> currentNode = queue.poll();
			currentSize --;
			
			
			if (currentNode.leftNode != null) {
				queue.offer(currentNode.leftNode);
			}
			if (currentNode.rightNode != null) {
				queue.offer(currentNode.rightNode);
			}
			
			if (currentSize == 0) {
				countLevel ++;
				currentSize = queue.size();
			}
	
		}
		
		return countLevel;
	}
	public void reverseTree() {
//		reverse(rootNode);
		//四种遍历都可以,中序遍历特殊
		
		reverseViaLevelOrder(rootNode);
	}
	
	protected void reverse(Node<E> rootNode) {
		if (rootNode == null) {
			
			return;
		}
		reverse(rootNode.leftNode);
		
		Node<E> currentTmpNode = rootNode.leftNode;
		rootNode.leftNode = rootNode.rightNode;
		rootNode.rightNode = currentTmpNode;
		
		reverse(rootNode.leftNode);
		
	}
	protected void reverseViaLevelOrder(Node<E> rootNode) {
		if (rootNode == null) {
			return;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		
		queue.offer(rootNode);
		
		while (queue.size()>0) {
			Node<E> currentNode = queue.poll();
			
			if (currentNode.leftNode !=null || currentNode.rightNode != null) {
				Node<E> tmpNode = currentNode.leftNode;
				currentNode.leftNode = currentNode.rightNode;
				currentNode.rightNode = tmpNode;
			}
			
			if (currentNode.leftNode != null) {
				queue.add(currentNode.leftNode);
			}
			
			if (currentNode.rightNode != null) {
				queue.add(currentNode.rightNode);
			}	
		}
	}
	public ArrayList<E> getPreorderArrayList() {
		ArrayList<E> arrayList = new ArrayList<>();
		preorderNodesInAList(rootNode, arrayList);
		return arrayList;
	}
	
	protected void preorderNodesInAList(Node<E> rootNode,ArrayList<E> arrayList) {
		if (rootNode == null) {
			return;
		}
		arrayList.add(rootNode.element);
		preorderNodesInAList(rootNode.leftNode, arrayList);
		preorderNodesInAList(rootNode.rightNode, arrayList);
		
	}
	
	public ArrayList<E> getInorderArrayList() {
		ArrayList<E> arrayList = new ArrayList<>();
		inorderNodesInAList(rootNode, arrayList);
		return arrayList;
	}
	
	protected void inorderNodesInAList(Node<E> rootNode,ArrayList<E> arrayList) {
		if (rootNode == null) {
			return;
		}
		inorderNodesInAList(rootNode.leftNode, arrayList);
		arrayList.add(rootNode.element);
		inorderNodesInAList(rootNode.rightNode, arrayList);
		
	}
	protected Node<E> recoverTree(
			ArrayList<E> preorderArrayList,
			ArrayList<E> inordArrayList)  {
		
		if (preorderArrayList.isEmpty()||inordArrayList.isEmpty()) {
			
			return null;
		}
		E rootElement = preorderArrayList.get(0);
		
		int indexInInorder = inordArrayList.indexOf(rootElement);
		
		ArrayList<E> leftNodesInorderArrayList = new ArrayList<>();
		ArrayList<E> rightNodesInorderArrayList = new ArrayList<>();
		
		for (int i = 0; i < indexInInorder; i++) {
			leftNodesInorderArrayList.add(inordArrayList.get(i));
		}
		for (int i = indexInInorder + 1; i < inordArrayList.size(); i++) {
			rightNodesInorderArrayList.add(inordArrayList.get(i));
		}
		
		ArrayList<E> leftNodesPreorderArrayList = new ArrayList<>();
		ArrayList<E> rightNodesPreorderArrayList = new ArrayList<>();
		
		for (int i = 1; i < preorderArrayList.size(); i++) {
			E elementE = preorderArrayList.get(i);
			if (leftNodesInorderArrayList.contains(elementE)) {
				leftNodesPreorderArrayList.add(elementE);
			}
		}
		
		for (int i = leftNodesPreorderArrayList.size()+1; i < preorderArrayList.size(); i++) {
			E elementE = preorderArrayList.get(i);
				rightNodesPreorderArrayList.add(elementE);
		}
		
		Node<E> rootNode = new Node<>(preorderArrayList.get(0));
		
		rootNode.leftNode = recoverTree(leftNodesPreorderArrayList, leftNodesInorderArrayList);
		rootNode.rightNode = recoverTree(rightNodesPreorderArrayList, rightNodesInorderArrayList);
		System.out.println(rootNode.element);
		return rootNode;
	}
	public boolean contains(E element) {
		return node(element) != null;
	}
	protected Node<E> node(E element) {
		if (rootNode == null) {
			return null;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		
		queue.offer(rootNode);
		
		while (queue.size()>0) {
			Node<E> headNode = queue.poll();
			
			if (headNode.element == element) {
				return headNode;
			}
			
			if (headNode.leftNode!=null) {
				queue.offer(headNode.leftNode);
			}
			
			if (headNode.rightNode!=null) {
				queue.offer(headNode.rightNode);
			}
		}
		return null;
	}
	
	public E findPrecursorNode(E element) {
		
		Node<E> preNode = findePreNode(node(element));
		if (preNode != null) {
			return preNode.element;
		}
		else {
			return null;
		}
	}
	protected Node<E> findePreNode(Node<E> rootNode) {
		if (rootNode == null) {
			return null;
		}
		
		if (rootNode.leftNode != null) {
			Node<E> currentNode = rootNode.leftNode;
			while (currentNode.rightNode != null) {
				currentNode = currentNode.rightNode;
			}
			return currentNode;
		}
		else if (rootNode.parentNode != null) {
			
			Node<E> currentNode = rootNode;
			while (currentNode.parentNode != null) {
				if (currentNode.parentNode.rightNode == currentNode) {
					return currentNode.parentNode;
				}
				currentNode = currentNode.parentNode;	
			}
			
		}
		
		return null;
	}
	
	public E findSuccessorNode(E element) {
		
		Node<E> preNode = findeSucNode(node(element));
		if (preNode != null) {
			return preNode.element;
		}
		else {
			return null;
		}
	}
	protected Node<E> findeSucNode(Node<E> rootNode) {
		if (rootNode == null) {
			return null;
		}
		
		if (rootNode.rightNode != null) {
			Node<E> currentNode = rootNode.rightNode;
			while (currentNode.leftNode != null) {
				currentNode = currentNode.leftNode;
			}
			return currentNode;
		}
		else if (rootNode.parentNode != null) {
			
			Node<E> currentNode = rootNode;
			while (currentNode.parentNode != null) {
				if (currentNode.parentNode.leftNode == currentNode) {
					return currentNode.parentNode;
				}
				currentNode = currentNode.parentNode;	
			}
			
		}
		
		return null;
	}
	public boolean isComplete() {
		
		if (rootNode==null) {
			return false;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(rootNode);
		
		boolean requireAllLeaf = false;
		
		while (queue.isEmpty() == false) {
			
			Node<E> currentNode = queue.poll();
			
			if (requireAllLeaf) {
				if (currentNode.leftNode != null || currentNode.rightNode != null) {
					return false;
				}
			}
			
			if (currentNode.leftNode != null) {
				queue.offer(currentNode.leftNode);
			}
			
			if (currentNode.rightNode != null) {
				queue.offer(currentNode.rightNode);
			}
			
			if(currentNode.rightNode == null) {
				requireAllLeaf = true;
			}
			else if (currentNode.leftNode == null) {
				return false;
			}

		}

		return true;
	}
	protected int judgeNodeDegree(Node<E> node) {
		
		int degree = 0;
		
		if (node.leftNode!=null) {
			degree++;
		}
		
		if (node.rightNode!=null) {
			degree++;
		}
		return degree;
	}
	@Override
	public Object root() {
		
		return rootNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object left(Object node) {
		return ((Node<E>)node).leftNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object right(Object node) {
		return ((Node<E>)node).rightNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object string(Object node) {
		return ((Node<E>)node).element + "";
	}
}
