package com.wy.trie;

import java.security.cert.TrustAnchor;
import java.util.HashMap;

public class Trie<V> {
	
	private int size = 0;
	private Node<V> rootNode;
	
	public Trie() {
		super();
		rootNode = new Node<>();
	}


	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	
	public V get(String key) {
		keyCheck(key);
		Node<V> resultNode = node(key);
		if (resultNode == null || resultNode.isWord == false) {
			return null;
		}
		return resultNode.value;
	}
	
	public boolean contains(String key) {
		Node<V> targetNode = node(key);
		return targetNode != null && targetNode.isWord;
	}
	
	public V add(String key, V value) {
		
		keyCheck(key);
		Node<V> currentNode = rootNode;

		V oldValue = null;
		int length = key.length();
		
		for (int i = 0; i < length; i++) {
			Character character = key.charAt(i);
			Node<V> nextNode = currentNode.getChildrenMap().get(character);
			if (nextNode != null) {
				currentNode = nextNode;
				oldValue = nextNode.value;
			}
			else {
				
				Node<V> newChildNode = new Node<>(currentNode, character);
				if (i == length - 1) {
					newChildNode.isWord = true;
					newChildNode.value = value;
					currentNode.getChildrenMap().put(character, newChildNode);
				}
				else {
					currentNode.getChildrenMap().put(character, newChildNode);
				}
				currentNode = newChildNode;
				oldValue = null;
			}
		}
		
		size ++;
		
		return oldValue;
	}
	
	public V remove(String key) {
		keyCheck(key);
		
		Node<V> targetNode = node(key);
		
		if (targetNode != null && targetNode.isWord) {
			size --;
		}
		else {
			return null;
		}
		
		V targetValue = null;
		if (targetNode.isWord && targetNode.getChildrenMap().size() > 0) {
			targetNode.isWord = false;
			targetValue = targetNode.value;
			targetNode.value = null;
		}
		else if (targetNode.isWord && targetNode.getChildrenMap().size() == 0) {
			
			Node<V> currentNode = targetNode;
			do {
				if (currentNode.parentNode == rootNode 
					|| currentNode.parentNode.getChildrenMap().size()>1
					|| currentNode.parentNode.isWord == true
					) {
					
					currentNode.parentNode.getChildrenMap().remove(currentNode.character);
					break;
				}
				currentNode = currentNode.parentNode;
			} while (currentNode != null && currentNode.parentNode != null);

			targetValue = targetNode.value;
		}
		else if (!targetNode.isWord) {
			return null;
		}
		
		return targetValue;
	}
	
	public boolean startWith(String string) {
		keyCheck(string);
		return node(string) != null;
	}
	
	private Node<V> node(String key) {
		keyCheck(key);
		int length = key.length();
		Node<V> currentNode = rootNode;
		for (int i = 0; i < length; i++) {
			
			Character character = key.charAt(i);
			Node<V> targetChildNode = currentNode.getChildrenMap().get(character);
			if (targetChildNode == null) {
				return null;
			}
			else {
				currentNode = targetChildNode;
			}
		}
		return currentNode;
	}
	
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}
	
	public static class Node<V> {
		
		HashMap<Character, Node<V>> childrenMap;
		boolean isWord;
		V value;
		Node<V> parentNode;
		Character character;
		
		
		
		
		public Node() {
			this(null, null);
		}

		public Node(Node<V> parentNode, Character character) {
			super();
			this.parentNode = parentNode;
			this.character = character;
		}

		public HashMap<Character, Node<V>> getChildrenMap() {
			return childrenMap == null ? childrenMap = new HashMap<>() : childrenMap;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
