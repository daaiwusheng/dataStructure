package com.wy.hashMap;

import com.wy.hashMap.HashMap.RedBlackNode;

public class LinkedHashMap<K, V> extends HashMap<K, V> {

	private LinkedRedBlackNode<K, V> firstNode;
	private LinkedRedBlackNode<K, V> lastNode;
	
	public static class LinkedRedBlackNode<K, V> extends RedBlackNode<K, V>{

		LinkedRedBlackNode<K, V> previousNode;
		LinkedRedBlackNode<K, V> nextNode;
		
		
		public LinkedRedBlackNode(K key, V value) {
			super(key, value);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@Override
	protected RedBlackNode<K, V> createNode(K key, V value) {
		
		LinkedRedBlackNode<K, V> newNode = new LinkedRedBlackNode<>(key, value);
		
		return newNode;
	}
	
	
	@Override
	protected void afterAddNewNodeForLinked(RedBlackNode<K, V> newNode) {
		LinkedRedBlackNode<K, V> currentNode = (LinkedRedBlackNode<K, V>)newNode;
		
		if (firstNode != null) {
			
			lastNode.nextNode = currentNode;
			currentNode.previousNode = lastNode;
			lastNode = currentNode;
			
		}
		else {
			firstNode = lastNode = currentNode;
		}
		
	}
	
	@Override
	public void clear() {
		super.clear();
		
		firstNode = null;
		lastNode = null;
		
	}
	
	@Override
	public void traversal(Visitor<K, V> visitor) {
		
		LinkedRedBlackNode<K, V> currentNode = firstNode;
		while (currentNode != null) {
			visitor.visit(currentNode.key, currentNode.value);
			currentNode = currentNode.nextNode;
		}
	}
	
	
	protected void afterRemoveForExchangeNodeInLinkedList(RedBlackNode<K, V> originalTargetNode, RedBlackNode<K, V> successorNode) {
		
		LinkedRedBlackNode<K, V> currentNode = (LinkedRedBlackNode<K, V>)originalTargetNode;
		LinkedRedBlackNode<K, V> currentSuccessorNode = (LinkedRedBlackNode<K, V>)successorNode;
		
	
			LinkedRedBlackNode<K, V> previousOfCurrentSuccessorNode = currentSuccessorNode.previousNode;
			LinkedRedBlackNode<K, V> nextOfCurrentSuccessorNode = currentSuccessorNode.nextNode;
			
			
			if (currentNode.previousNode != null) {
				currentNode.previousNode.nextNode = currentSuccessorNode;
				currentSuccessorNode.previousNode = currentNode.previousNode;
			}
			else {
				firstNode = currentSuccessorNode;
				firstNode.previousNode = null;
			}
			
			if (currentNode.nextNode != null) {
				currentNode.nextNode.previousNode = currentSuccessorNode;
				currentSuccessorNode.nextNode = currentNode.nextNode;
			}
			else {
				lastNode = currentSuccessorNode;
				lastNode.nextNode = null;
			}
			
			if (previousOfCurrentSuccessorNode != null) {
				previousOfCurrentSuccessorNode.nextNode = currentNode;
				currentNode.previousNode = previousOfCurrentSuccessorNode;
			}
			else {
				firstNode = currentNode;
				firstNode.previousNode = null;
			}

			if (nextOfCurrentSuccessorNode != null) {
				nextOfCurrentSuccessorNode.previousNode = currentNode;
				currentNode.nextNode = nextOfCurrentSuccessorNode;
			}
			else {
				lastNode = currentNode;
				lastNode.nextNode = null;
			}
			
		
		

	}
	
	protected void afterRemoveForLinked(RedBlackNode<K, V> targetNode) {
		
		LinkedRedBlackNode<K, V> currentNode = (LinkedRedBlackNode<K, V>)targetNode;
		
		if (currentNode.previousNode != null) {
			currentNode.previousNode.nextNode = currentNode.nextNode;
		}
		else {
			firstNode = currentNode.nextNode;
			
			firstNode.previousNode = null;
		}
		
		if (currentNode.nextNode != null) {
			currentNode.nextNode.previousNode = currentNode.previousNode;
		}
		else {
			lastNode = currentNode.previousNode;
			lastNode.nextNode = null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
