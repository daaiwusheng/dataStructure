package com.wy.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.wy.binaryTree.RedBlackTree.RedBlackNode;
import com.wy.map.TreeMap.RedBlackNode.NodeChildType;
import com.wy.map.TreeMap.RedBlackNode.NodeColorType;

public class TreeMap<K, V> implements Map<K, V> {

	
	public int size;
	public RedBlackNode<K, V> rootNode;
	public Comparator<K> comparator;
	
	
	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	public static class RedBlackNode<K, V> {

		NodeColorType color = NodeColorType.RED;
		
		K key;
		
		V value;
		
		RedBlackNode<K, V> leftNode;
		RedBlackNode<K, V> rightNode;
		RedBlackNode<K, V> parentNode;
		
		public RedBlackNode(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public static enum NodeChildType{
			Left,Right,Root;
		}
		
		public static enum NodeColorType{
			RED,BLACK;
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
		
		private RedBlackNode<K, V> siblingNode() {
			
			NodeChildType type = judgeChildType();
			if (type == NodeChildType.Left) {
				return parentNode.rightNode;
			}
			else if (type == NodeChildType.Right) {
				return parentNode.leftNode;
			}
			else {
				return null;
			}
			
		}
		
	}
	
	
	private	enum compareTwoNode
	{
		Left_ParentIsBigger,Right_ParentIsSmaller,Equal;
		
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		rootNode = null;
		size = 0;
	}

	@Override
	public V put(K key, V value) {
		RedBlackNode<K, V> newNode = new RedBlackNode<>(key, value);
		
		if (isEmpty()) {
			rootNode = newNode;
		}
		else {
			RedBlackNode<K, V> targetNode = rootNode;
			RedBlackNode<K, V> parentNode = rootNode;
			
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
					targetNode.key = newNode.key;
					targetNode.value = newNode.value;
					afterReplaceByNewNode(newNode,targetNode);
					return newNode.value;
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
		return newNode.value;
	}

	private void afterReplaceByNewNode(RedBlackNode<K, V> newNode,RedBlackNode<K, V> oldNode) {
		newNode.color = oldNode.color;
	}
	
	private void afterAddNewNode(RedBlackNode<K, V> node) {
		RedBlackNode<K,V> parentNode = (RedBlackNode<K,V>)node.parentNode;
		
		if (parentNode == null) {
			//it means node is rootNode, so color it as black
			black((RedBlackNode<K,V>)node);
			return;
		}
		
		if (isBlack(parentNode)) {
			//here, we do not need to do anything
			return;
		}
		
		RedBlackNode<K,V> uncleNode = parentNode.siblingNode();
		RedBlackNode<K,V> grandNode = (RedBlackNode<K,V>)parentNode.parentNode;
		
		if (isRed(uncleNode)) {
			//color uncleNode and parentNode as black, and grandNode as red.
			black(uncleNode);
			black(parentNode);
			red(grandNode);
			afterAddNewNode(grandNode);
			return;
		}
		
		//if come here, parentNode is red, uncleNode(null) is black, grandNode must be black
		NodeChildType currentNodeChildType = node.judgeChildType();
		NodeChildType parentNodeChildType = parentNode.judgeChildType();
		if (parentNodeChildType == NodeChildType.Left) {//L
			if (currentNodeChildType == NodeChildType.Left) {//LL
				red(grandNode);
				black(parentNode);
				rotateToRight(grandNode, parentNode);
			}
			else {//LR
				red(grandNode);
				black((RedBlackNode<K,V>)node);
				
				rotateToLeft(parentNode, node);
				rotateToRight(grandNode, node);
			}
		}
		else {//R
			if (currentNodeChildType == NodeChildType.Left) {//RL
				red(grandNode);
				black((RedBlackNode<K,V>)node);
				rotateToRight(parentNode, node);
				rotateToLeft(grandNode, node);
				
			}
			else {//RR
				red(grandNode);
				black(parentNode);
				rotateToLeft(grandNode, parentNode);
			}
		}
	}
	
	private compareTwoNode compare(RedBlackNode<K, V> nodeInTree, RedBlackNode<K, V> newNode) {
		return compareTwoKey(nodeInTree.key,newNode.key);
	}
	
	@SuppressWarnings("unchecked")
	private compareTwoNode compareTwoKey(K keyInTree, K key) {
		int compareResult;
		if (comparator != null) {
			compareResult = comparator.compare(keyInTree, key);
		}
		else {
			compareResult = ((Comparable<K>)keyInTree).compareTo(key);
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
	
	private RedBlackNode<K, V> node(K key) {
		
		RedBlackNode<K, V> currentNode = rootNode;
		compareTwoNode result = compareTwoNode.Left_ParentIsBigger;
		
		while (currentNode != null) {
			result = compareTwoKey(currentNode.key, key);
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

	public void deleteElement(K key) {
		
		RedBlackNode<K, V> targetNode = node(key);
		
		if (targetNode==null) {
			return;
		}
		else {
			size --;
		}
		
		if (judgeNodeDegree(targetNode) == 2) {
			//一定有前驱和后继节点,并且前驱和后继节点的度一定是1或者0
			
			RedBlackNode<K, V> successorNode = findeSucNode(targetNode);
			targetNode.key = successorNode.key;
			targetNode.value = successorNode.value;
			targetNode = successorNode;
		}
		//targetNode is the real one that has been removed
		if (targetNode.rightNode != null||targetNode.leftNode != null) {
			RedBlackNode<K, V> nextNode = (targetNode.rightNode!=null)?targetNode.rightNode:targetNode.leftNode;
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
	
	private int judgeNodeDegree(RedBlackNode<K, V> node) {
		
		int degree = 0;
		
		if (node.leftNode!=null) {
			degree++;
		}
		
		if (node.rightNode!=null) {
			degree++;
		}
		return degree;
	}
	
	protected void afterDelete(RedBlackNode<K,V> deletedNode) {
		RedBlackNode<K,V> redBlackReplaceNode = (RedBlackNode<K,V>)deletedNode;
		if (isRed(redBlackReplaceNode)) {
			//1.父类传入的是用于替代被删除节点的节点,而删除的只可能是叶子节点,此时
			//如果这个节点用于替代其父节点,那么说明其父节点是黑色节点,并且度为1,此时染黑替代节点即可
			//2.如果此时传入的节点是真正要被删除的节点,说明它是要删除节点的前驱或者后继节点,染黑并无妨碍,因为
			//它已经不在树中了
			black(redBlackReplaceNode);
			return;
		}
		
		RedBlackNode<K,V> redBlackDeleteNode = (RedBlackNode<K,V>)deletedNode;
		
		if (rootNode == redBlackDeleteNode) {
			black(redBlackDeleteNode);
			return;
		}
		
		if (isBlack(redBlackDeleteNode)) {
			//被删除节点如果是黑色,那么它一定有兄弟节点,否则不会满足性质5,
			//而此时被删除节点已经为父节点删除了,所以siblingNode() 这个方法已经不好用了
			RedBlackNode<K,V> parentNode = (RedBlackNode<K,V>)redBlackDeleteNode.parentNode;
			RedBlackNode<K,V> siblingNode = redBlackDeleteNode.siblingNode();
			if (siblingNode == null ) {
				if (parentNode.leftNode != null) {
					siblingNode = (RedBlackNode<K,V>)parentNode.leftNode;
				}
				else {
					siblingNode = (RedBlackNode<K,V>)parentNode.rightNode;
				}
			}
			
			if (isRed(siblingNode)) {
				//那么将sibling 染成 black, parent 染成 red,然后进行旋转,
				//就会继续维持sibling是black的情况,可以继续走下面的代码
				black(siblingNode);
				red(parentNode);
				
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Left) {
					//sibling要么是左子节点,要么就是右子节点,不可能是根节点,因为父节点不为空
					//此时父节点右旋转
					rotateToRight(parentNode, siblingNode);
					//旋转之前,被删除节点为黑色,并且其兄弟节点是红色,那么证明兄弟节点必定有两个黑色子节点,否则不满足性质5
					//所以,旋转后,被删除节点的兄弟节点一定是存在的,并且和旋转方向相反
					siblingNode = (RedBlackNode<K,V>)parentNode.leftNode;
				}
				else {
					rotateToLeft(parentNode, siblingNode);	
					siblingNode = (RedBlackNode<K,V>)parentNode.rightNode;
				}
				
			}
			
			//判断兄弟节点是否有红色子节点
			if (isRed((RedBlackNode<K,V>)siblingNode.leftNode)&&isRed((RedBlackNode<K,V>)siblingNode.rightNode)) {
				//兄弟节点有两个红色子节点,那么无论兄弟节点是左子节点还是右子节点,都可以只旋转一次
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Left) {
					//说明是LL的情况,那么父节点右旋转即可
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<K,V>)siblingNode.leftNode);
					black(parentNode);
					rotateToRight(parentNode, siblingNode);
				}
				else {
					//RR, 父节点向左旋转
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<K,V>)siblingNode.rightNode);
					black(parentNode);
					rotateToLeft(parentNode, siblingNode);
				}
			}
			else if (isRed((RedBlackNode<K,V>)siblingNode.leftNode)) {
				//兄弟节点只有左侧子节点是红色
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Left) {
					//说明是LL的情况,那么父节点右旋转即可
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<K,V>)siblingNode.leftNode);
					black(parentNode);
					rotateToRight(parentNode, siblingNode);
				}
				else {
					//RL, 先对sibling 向右旋转,然后再对parent 向左旋转
					color((RedBlackNode<K,V>)siblingNode.leftNode, colorOf(parentNode));
					black(parentNode);
					rotateToRight(siblingNode, siblingNode.leftNode);
					rotateToLeft(parentNode, parentNode.rightNode);
				}

			}
			else if (isRed((RedBlackNode<K,V>)siblingNode.rightNode)) {
				//兄弟节点只有右侧子节点是红色
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Right) {
					//RR, 父节点向左旋转
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<K,V>)siblingNode.rightNode);
					black(parentNode);
					rotateToLeft(parentNode, siblingNode);
				}
				else {
					//说明是LR的情况,先对sibling进行左旋转,然后再对parent进行右旋转
					color((RedBlackNode<K,V>)siblingNode.rightNode, colorOf(parentNode));
					black(parentNode);
					rotateToLeft(siblingNode, siblingNode.rightNode);
					rotateToRight(parentNode, parentNode.leftNode);
					
				}
			}
			else {
				//黑色节点的兄弟节点是黑色,但是兄弟节点没有子节点,
				//此时会造成当前节点下溢,并且兄弟节点无法借过来子节点,只能从父节点借,弥补下溢.
				//此时如果父节点依然是黑色,那么父节点也会下溢,要把父节点当成被删除的节点再次走当前函数,递归
				boolean isParentBlack = isBlack(parentNode);
				red(siblingNode);
				if (!isParentBlack) {
					black(parentNode);
				}

				if (isParentBlack) {
					afterDelete(parentNode);
				}
			}	
		}
	}
	
	protected void rotateToLeft(RedBlackNode<K,V> upperNode, RedBlackNode<K,V> lowerNode) {
		
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
	
	protected void rotateToRight(RedBlackNode<K,V> upperNode, RedBlackNode<K,V> lowerNode) {
		
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
	
	private NodeColorType colorOf(RedBlackNode<K,V> node) {
		return node == null?NodeColorType.BLACK:node.color;
	}
	
	private boolean isBlack(RedBlackNode<K,V> node) {
		return colorOf(node) == NodeColorType.BLACK;
	}
	
	private boolean isRed(RedBlackNode<K,V> node) {
		return colorOf(node) == NodeColorType.RED;
	}
	
	private RedBlackNode<K,V> color(RedBlackNode<K,V> node, NodeColorType color) {
		if (node != null) {
			node.color = color;
		}
		return node;
	}
	
	private RedBlackNode<K,V> black(RedBlackNode<K,V> node) {
		return color(node, NodeColorType.BLACK);
	}
	
	private RedBlackNode<K,V> red(RedBlackNode<K,V> node) {
		return color(node, NodeColorType.RED);
	}

	
	private RedBlackNode<K,V> findeSucNode(RedBlackNode<K,V> rootNode) {
		if (rootNode == null) {
			return null;
		}
		
		if (rootNode.rightNode != null) {
			RedBlackNode<K,V> currentNode = rootNode.rightNode;
			while (currentNode.leftNode != null) {
				currentNode = currentNode.leftNode;
			}
			return currentNode;
		}
		else if (rootNode.parentNode != null) {
			
			RedBlackNode<K,V> currentNode = rootNode;
			while (currentNode.parentNode != null) {
				if (currentNode.parentNode.leftNode == currentNode) {
					return currentNode.parentNode;
				}
				currentNode = currentNode.parentNode;	
			}
			
		}
		
		return null;
	}

	
	@Override
	public V get(K key) {
		
		RedBlackNode<K, V> node = node(key);
		
		if (node!= null) {
			return node.value;
		}
		
		return null;
	}

	@Override
	public V remove(K key) {
		V vlaue = node(key).value;
		deleteElement(key);
		return vlaue;
	}

	@Override
	public boolean containsKey(K key) {
		
		if (node(key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		
		Queue<RedBlackNode<K, V>> queue = new LinkedList<>();
		
		queue.add(rootNode);
		
		while (queue.size()>0) {
			
			RedBlackNode<K, V> nodeInQueue = queue.poll();
			
			V valueInTree = nodeInQueue.value;
			if (value != null) {
				if (value.equals(valueInTree)) {
					return true;
				}
			}
			else {
				if (valueInTree == null) {
					return true;
				}
			}
			
			if (nodeInQueue.leftNode != null) {
				queue.add(nodeInQueue.leftNode);
			}
			
			if (nodeInQueue.rightNode != null) {
				queue.add(nodeInQueue.rightNode);
			}
		}
		
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		inorder(rootNode, visitor);
	}
	
	private void inorder(RedBlackNode<K, V> rootNode, Visitor<K,V> visitor) {
		
		if (rootNode == null|| visitor.stop) {
			return;
		}
		inorder(rootNode.leftNode,visitor);
		
		if (visitor.stop) {
			return;
		}
		
		visitor.visit(rootNode.key,rootNode.value);
		
		inorder(rootNode.rightNode,visitor);
		
	}

}
