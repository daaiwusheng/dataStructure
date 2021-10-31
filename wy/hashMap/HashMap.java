package com.wy.hashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;



import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;
import com.wy.hashMap.HashMap.RedBlackNode.NodeChildType;
import com.wy.hashMap.HashMap.RedBlackNode.NodeColorType;
import com.wy.map.Map;
import com.wy.testCases.Key;


@SuppressWarnings("unchecked")

public class HashMap<K, V> implements Map<K, V> {

	private	int size;
	private RedBlackNode<K, V> [] table;
	private static final int DEFALUT_SIZE = 1<<4;
	private static final float CAPACITY_FACTOR = 0.75f;
	
	public HashMap() {
		table = new RedBlackNode[DEFALUT_SIZE];
		
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
		size = 0;
        for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}
	
	
	@Override
	public V put(K key, V value) {
		
		resize();
		
		RedBlackNode<K, V> newNode = createNode(key, value);
		
		RedBlackNode<K, V> rootNode = table[indexByKey(key)];

		if (rootNode == null) {
			rootNode = newNode;
			table[indexByKey(key)] = rootNode;
		}
		else {
			RedBlackNode<K, V> currentNode = rootNode;
			RedBlackNode<K, V> parentNode = rootNode;
			
			compareTwoNode directionForAdd = null;
			
			boolean hasSearched = false;
			int hashCodeOfKey = newNode.hashCode;
			
			do {
				parentNode = currentNode;
				int compareResult;
				K keyInTree = currentNode.key;
				int hashCodeOfKeyInTree = currentNode.hashCode;
				
				//1.先看是否都为空,因为为空就认为哈希值是0,就不用往下走了
				if (keyInTree == null && key == null) {
					compareResult = 0;
				}
				//2.如果一个为空,另一个不为空
				//这种情况显然是,不能认为相等,但此时,她们的哈希值也是有可能相等的,
				//可以直接认为不空对象,大于null对象
				else if (keyInTree == null && key != null) {
					//Right_ParentIsSmaller;
					compareResult = -1;
				}
				else if (keyInTree != null && key == null) {
					//Left_ParentIsBigger;
					compareResult = 1;
				}	
				
				//**以下都是 不为空的情况了
				//如果两者是相同的地址,那肯定是相同的key
				//如果两者不相等,并且实现了equals方法,并且结果是true,那么就认定她们是相等的key
				else if (Objects.equals(keyInTree, key)) {
					compareResult = 0;//a == b) || (a != null && a.equals(b));
				}
				//3.哈希值有可能不同
				else if (hashCodeOfKeyInTree > hashCodeOfKey) {
					//Left_ParentIsBigger;
					compareResult = 1;
				}
				else if (hashCodeOfKeyInTree < hashCodeOfKey) {
					//Right_ParentIsSmaller;
					compareResult = -1;
				}
				else {
					//4.如果类型不同,那么可以比较出一个大小来
					String classKeyInTree = keyInTree.getClass().getName();
					String classKey = key.getClass().getName();
					 
					//来到这里说明是同一种类型的,并且哈希值相同,
					//equals方法比较出来的结果是不相同的,但是有可能是不同的对象
					//同一种类型,且具有可比较性,那么可以用比较方法
					if(classKeyInTree.equals(classKey) && keyInTree instanceof Comparable
							&& (compareResult = ((Comparable)keyInTree).compareTo(key))!=0)
					{
					}
					else {
						//能到这里如果直接去比较内存地址,那么当再次出现"哈希值相同,类型相同,equals也相同的key值的时候",就会
						//很可能也要走到比较地址的代码,因为以上的代码都没有返回结果,这样也有可能完美的错过真正需要到达的定位点
						//所以这里应该是遍历整棵树,匹配结果,匹配不到,然后在用内存地址判断大小,将节点添加到树中;如果是查找某个key是否
						//存在于树中,这个判断一定会找到节点的
						
						RedBlackNode<K, V> resultNode = null;
						
						if (!hasSearched && currentNode.leftNode != null && (resultNode = node(currentNode.leftNode, key)) != null) {
							currentNode = resultNode;
							compareResult = 0;
						}
						else if (!hasSearched && currentNode.rightNode != null && (resultNode = node(currentNode.rightNode, key)) != null) {
							currentNode = resultNode;
							compareResult = 0;
						}
						else {
							//比较地址
							compareResult = System.identityHashCode(keyInTree)-System.identityHashCode(key);
							hasSearched = true;
						}
					}
					

				}
				
				
								
				directionForAdd = getCompareType(compareResult);
				if (directionForAdd == compareTwoNode.Left_ParentIsBigger) {
					//currentNode is bigger
					currentNode = currentNode.leftNode;
				}
				else if (directionForAdd == compareTwoNode.Right_ParentIsSmaller) {
					//currentNode is smaller
					currentNode = currentNode.rightNode;
				}
				else {
					newNode.leftNode = currentNode.leftNode;
					newNode.rightNode = currentNode.rightNode;
					newNode.parentNode = currentNode.parentNode;
					currentNode.key = newNode.key;
					currentNode.value = newNode.value;
					currentNode.hashCode = newNode.hashCode;
					afterReplaceByNewNode(newNode,currentNode);
					return newNode.value;
				}
			} while (currentNode != null);
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
		afterAddNewNodeForLinked(newNode);
		return newNode.value;

	}

	protected void afterAddNewNodeForLinked(RedBlackNode<K, V> newNode) {
		
	}
		
	@Override
	public V get(K key) {
		
		int index = indexByKey(key);
		RedBlackNode<K, V> rootNode = table[index];
		if (rootNode == null) {
			return null;
		}
		
		RedBlackNode<K, V> node = node(rootNode, key);
		if (node != null) {
			return node.value;
		}
		return null;
	}

	@Override
	public V remove(K key) {
		
		int index = indexByKey(key);
		RedBlackNode<K, V> rootNode = table[index];
		if (rootNode == null) {
			return null;
		}
		RedBlackNode<K, V> node = node(rootNode, key);
		V vlaue = node != null?node.value:null;
		deleteElement(key);
		return vlaue;
	}
	@Override
	public boolean containsKey(K key) {
		
		int index = indexByKey(key);
		RedBlackNode<K, V> rootNode = table[index];
		if (rootNode == null) {
			return false;
		}
		
		if (node(rootNode, key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		Queue<RedBlackNode<K, V>> queue = new LinkedList<>();
		int length = table.length;
		for (int i = 0; i < length; i++) {
			RedBlackNode<K, V> rootNode = table[i];
			if (rootNode == null) {
				continue;
			}
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

		}
		
				
		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		int length = table.length;
		
		for (int i = 0; i < length; i++) {
			
			final RedBlackNode<K, V> rootNode = table[i];
			System.out.println("-----------------------------------------------");
			System.out.println("index = " + i);
			if (rootNode == null) {
				continue;
			}
			
			BinaryTrees.println(new BinaryTreeInfo() {
				
				@Override
				public Object string(Object node) {
					return node.toString();
				}
				
				@Override
				public Object root() {
					return rootNode;
				}
				
				@Override
				public Object right(Object node) {
					return ((RedBlackNode<K, V>)node).rightNode;
				}
				
				@Override
				public Object left(Object node) {
					return ((RedBlackNode<K, V>)node).leftNode;
				}
			});
		}
		
		

	}

	public static class RedBlackNode<K, V> {

		NodeColorType color = NodeColorType.RED;
		
		K key;
		
		V value;
		
		int hashCode;
		
		RedBlackNode<K, V> leftNode;
		RedBlackNode<K, V> rightNode;
		RedBlackNode<K, V> parentNode;
		
		public RedBlackNode(K key, V value) {
			this.key = key;
			this.value = value;
			this.hashCode = getHashCodeByKey(key);
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
		
		public int getHashCodeByKey(K key) {
			int hashCode = key == null?0:key.hashCode();
			hashCode = hashCode ^ (hashCode >>> 16);
			return hashCode;
		}
		
		@Override
		public String toString() {
			
			StringBuilder stringBuilder = new StringBuilder();
			
			if (color == NodeColorType.RED) {
				stringBuilder.append("$R");
			}
			
			stringBuilder.append("K_");
			if (key == null) {
				stringBuilder.append("null");
			}
			else {
				stringBuilder.append(key);
			}
			
			stringBuilder.append(";V_");
			if (value == null) {
				stringBuilder.append("null");
			}
			else {
				stringBuilder.append(value);
			}
			
			return stringBuilder.toString();
		}
		
	}
	
	private	enum compareTwoNode
	{
		Left_ParentIsBigger,Right_ParentIsSmaller,Equal;
		
	}
	/***
	 * calculate the index of table via key.
	 * @param key
	 * @return
	 */
	private int indexByKey(K key) {
		
		int hashCode = getHashCodeByKey(key);
		return getIndexByHashCode(hashCode);
	}
	
	private int indexByNode(RedBlackNode<K, V> node) {
		int hashCode = node.hashCode;
		return getIndexByHashCode(hashCode);
	}
	
	private void resize() {
		float factor = size / table.length;
		if (factor > CAPACITY_FACTOR) {
			RedBlackNode<K, V>[] oldTable = table;
			int newSize = oldTable.length << 1;
			table = new RedBlackNode[newSize];
			
			int length = oldTable.length;
			
			for (int i = 0; i < length; i++) {
				
				RedBlackNode<K, V> rootNode = oldTable[i];
				if (rootNode == null) {
					continue;
				}
				else {
					Queue<RedBlackNode<K, V>> queue = new LinkedList<>();
					queue.offer(rootNode);
					
					do {
						RedBlackNode<K, V> currentNode = queue.poll();
						
						currentNode.parentNode = null;
						
						if (currentNode.leftNode != null) {
							queue.offer(currentNode.leftNode);
							currentNode.leftNode = null;
						}
						
						if (currentNode.rightNode != null) {
							queue.offer(currentNode.rightNode);
							currentNode.rightNode = null;
						}
						
						int newIndex = indexByNode(currentNode);
						
						RedBlackNode<K, V> root = table[newIndex];
						if (root == null) {
							black(currentNode);
							table[newIndex] = currentNode;
						}
						else {
							red(currentNode);
							moveNode(root, currentNode);
						}
						
					} while (!queue.isEmpty());
				}
			}			
		}
	}
	
	private void moveNode(RedBlackNode<K, V> rootNode, RedBlackNode<K, V> newNode) {
		

		RedBlackNode<K, V> currentNode = rootNode;
		RedBlackNode<K, V> parentNode = rootNode;
			
		compareTwoNode directionForAdd = null;
			
		int hashCodeOfKey = newNode.hashCode;
			
		K key = newNode.key;
			
		do {
			parentNode = currentNode;
			int compareResult;
			K keyInTree = currentNode.key;
			int hashCodeOfKeyInTree = currentNode.hashCode;
				
			if (keyInTree == null && key != null) {
				//Right_ParentIsSmaller;
				compareResult = -1;
			}
			else if (keyInTree != null && key == null) {
				//Left_ParentIsBigger;
				compareResult = 1;
			}
			else if (hashCodeOfKeyInTree > hashCodeOfKey) {
				//Left_ParentIsBigger;
				compareResult = 1;
			}
			else if (hashCodeOfKeyInTree < hashCodeOfKey) {
				//Right_ParentIsSmaller;
				compareResult = -1;
			}
			else {
				String classKeyInTree = keyInTree.getClass().getName();
				String classKey = key.getClass().getName();

				if(classKeyInTree.equals(classKey) && keyInTree instanceof Comparable
						&& (compareResult = ((Comparable)keyInTree).compareTo(key))!=0)
				{
				}
				else {
					//比较地址
					compareResult = System.identityHashCode(keyInTree)-System.identityHashCode(key);
				}
			}			
			directionForAdd = getCompareType(compareResult);
			if (directionForAdd == compareTwoNode.Left_ParentIsBigger) {
				//currentNode is bigger
				currentNode = currentNode.leftNode;
			}
			else {
				//currentNode is smaller
				currentNode = currentNode.rightNode;
			}
		} while (currentNode != null);
		
		if (directionForAdd == compareTwoNode.Left_ParentIsBigger) {
			parentNode.leftNode = newNode;
			newNode.parentNode = parentNode;
		}
		else {
			parentNode.rightNode = newNode;
			newNode.parentNode = parentNode;
		}

		afterAddNewNode(newNode);
	}
	
	private int getIndexByHashCode(int hashCode) {
		int length = table.length;
		int index = (length-1)&hashCode;
		return index;
	}
	
	private int getHashCodeByKey(K key) {
		int hashCode = key == null?0:key.hashCode();
		hashCode = hashCode ^ (hashCode >>> 16);
		return hashCode;
	}
	
	private compareTwoNode compare(RedBlackNode<K, V> nodeInTree, RedBlackNode<K, V> newNode) {
		return compareTwoKey(nodeInTree.key,newNode.key);
	}
	
	private compareTwoNode compareTwoKey(K keyInTree, K key) {
		int compareResult;
		//1.先看是否都为空,因为为空就认为哈希值是0,就不用往下走了
		if (keyInTree == null && key == null) {
			return compareTwoNode.Equal;
		}
		//2.如果一个为空,另一个不为空
		//这种情况显然是,不能认为相等,但此时,她们的哈希值也是有可能相等的,
		//可以直接认为不空对象,大于null对象
		if (keyInTree == null && key != null) {
			return compareTwoNode.Right_ParentIsSmaller;
		}
		else if (keyInTree != null && key == null) {
			return compareTwoNode.Left_ParentIsBigger;
		}	
		
		//**以下都是 不为空的情况了
		//如果两者是相同的地址,那肯定是相同的key
		//如果两者不相等,并且实现了equals方法,并且结果是true,那么就认定她们是相等的key
		if (Objects.equals(keyInTree, key)) {
			return compareTwoNode.Equal;//a == b) || (a != null && a.equals(b));
		}
		//3.哈希值有可能不同
		int hashCodeOfKeyInTree = keyInTree.hashCode();
		int hashCodeOfKey = key.hashCode();
		if (hashCodeOfKeyInTree > hashCodeOfKey) {
			return compareTwoNode.Left_ParentIsBigger;
		}
		else if (hashCodeOfKeyInTree < hashCodeOfKey) {
			return compareTwoNode.Right_ParentIsSmaller;
		}
		
		//4.如果类型不同,那么可以比较出一个大小来
		String classKeyInTree = keyInTree.getClass().getName();
		String classKey = key.getClass().getName();
		compareResult = classKeyInTree.compareTo(classKey);
		if (compareResult != 0) {
			return getCompareType(compareResult);
		}
		
		//来到这里说明是同一种类型的,并且哈希值相同,
		//equals方法比较出来的结果是不相同的,但是有可能是不同的对象
		
		
		//同一种类型,且具有可比较性,那么可以用比较方法
		if(compareResult == 0 && (keyInTree instanceof Comparable))
		{
			compareResult = ((Comparable)keyInTree).compareTo(key);
			return getCompareType(compareResult);
			
		}
		
		//能到这里如果直接去比较内存地址,那么当再次出现"哈希值相同,类型相同,equals也相同的key值的时候",就会
		//很可能也要走到比较地址的代码,因为以上的代码都没有返回结果,这样也有可能完美的错过真正需要到达的定位点
		//所以这里应该是遍历整棵树,匹配结果,匹配不到,然后在用内存地址判断大小,将节点添加到树中;如果是查找某个key是否
		//存在于树中,这个判断一定会找到节点的
		
		
		
		return getCompareType(System.identityHashCode(keyInTree) - System.identityHashCode(key));		
	}
	
	private int getHashCodeByString(String string) {
		
		int	hashcode = 0;
		int size = string.length();
		for (int i = 0; i < size; i++) {
			char c = string.charAt(i);
			hashcode = hashcode * 31 + c;	
		}	
		
		return hashcode;
	}
	
	private compareTwoNode getCompareType(int compareResult) {
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
			table[indexByNode(upperNode)] = lowerNode;
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
			table[indexByNode(upperNode)] = lowerNode;
			lowerNode.parentNode = null;
		}
		
		upperNode.leftNode = lowerNode.rightNode;
		if (upperNode.leftNode!=null) {
			upperNode.leftNode.parentNode = upperNode;
		}
		
		lowerNode.rightNode = upperNode;
		upperNode.parentNode = lowerNode;	
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

	
	private RedBlackNode<K, V> node(RedBlackNode<K, V> rootNode,K key) {
		
		RedBlackNode<K, V> currentNode = rootNode;	
		int hashCodeOfKey = getHashCodeByKey(key);
		while (currentNode != null) {
			int compareResult = 0;
			K keyInTree = currentNode.key;
			int hashCodeOfKeyInTree = currentNode.hashCode;
			
			RedBlackNode<K, V> resultNode = null;
			//1.先看是否都为空,因为为空就认为哈希值是0,就不用往下走了
			if (keyInTree == null && key == null) {
				return currentNode;
			}
			//2.如果一个为空,另一个不为空
			//这种情况显然是,不能认为相等,但此时,她们的哈希值也是有可能相等的,
			//可以直接认为不空对象,大于null对象
			else if (keyInTree == null && key != null) {
				//Right_ParentIsSmaller;
				currentNode = currentNode.rightNode;
			}
			else if (keyInTree != null && key == null) {
				//Left_ParentIsBigger;
				currentNode = currentNode.leftNode;
			}	
			
			//**以下都是 不为空的情况了
			//如果两者是相同的地址,那肯定是相同的key
			//如果两者不相等,并且实现了equals方法,并且结果是true,那么就认定她们是相等的key
			else if (Objects.equals(keyInTree, key)) {
				return currentNode;//a == b) || (a != null && a.equals(b));
			}
			//3.哈希值有可能不同
			
			else if (hashCodeOfKeyInTree > hashCodeOfKey) {
				//Left_ParentIsBigger;
				currentNode = currentNode.leftNode;
			}
			else if (hashCodeOfKeyInTree < hashCodeOfKey) {
				//Right_ParentIsSmaller;
				currentNode = currentNode.rightNode;
			}
			else {
				//4.如果类型不同,那么可以比较出一个大小来
				String classKeyInTree = keyInTree.getClass().getName();
				String classKey = key.getClass().getName();
				
				
				//来到这里说明是同一种类型的,并且哈希值相同,
				//equals方法比较出来的结果是不相同的,但是有可能是不同的对象
				
				
				//同一种类型,且具有可比较性,那么可以用比较方法
				if(classKeyInTree.equals(classKey)&&
						keyInTree instanceof Comparable 
						&& (compareResult = ((Comparable)keyInTree).compareTo(key)) != 0)
				{
					compareTwoNode compareType = getCompareType(compareResult);
					if (compareType == compareTwoNode.Left_ParentIsBigger) {
						currentNode = currentNode.leftNode;
					}
					else {
						currentNode = currentNode.rightNode;
					} 
				}

				//能到这里如果直接去比较内存地址,那么当再次出现"哈希值相同,类型相同,equals也相同的key值的时候",就会
				//很可能也要走到比较地址的代码,因为以上的代码都没有返回结果,这样也有可能完美的错过真正需要到达的定位点
				//所以这里应该是遍历整棵树,匹配结果,匹配不到,然后在用内存地址判断大小,将节点添加到树中;如果是查找某个key是否
				//存在于树中,这个判断一定会找到节点的
				else if (currentNode.leftNode != null && (resultNode = node(currentNode.leftNode, key)) != null) {
					return resultNode;
				}
				else {
					currentNode = currentNode.rightNode;
				}
//				else if (currentNode.rightNode != null && (resultNode = node(currentNode.rightNode, key)) != null) {
//					return resultNode;
//				}
//				else {
//					return null;
//				}
				
			}
			
			
		}
		
		return null;		
	}
	
	
	private void deleteElement(K key) {
		
		RedBlackNode<K, V> rootNode = table[indexByKey(key)];
		if (rootNode == null) {
			return;
		}
		
		RedBlackNode<K, V> originalTargetNode = node(rootNode, key);
		RedBlackNode<K, V> targetNode = originalTargetNode;
		
		if (targetNode==null) {
			return;
		}
		else {
			size --;
		}
		RedBlackNode<K, V> successorNode = null;
		if (judgeNodeDegree(targetNode) == 2) {
			//一定有前驱和后继节点,并且前驱和后继节点的度一定是1或者0
			
			successorNode = findeSucNode(targetNode);
			targetNode.key = successorNode.key;
			targetNode.value = successorNode.value;
			targetNode.hashCode = successorNode.hashCode;
			targetNode = successorNode;
			
			afterRemoveForExchangeNodeInLinkedList(originalTargetNode, successorNode);

		}
		//targetNode is the real one that has been removed

		afterRemoveForLinked(targetNode);
		
		if (targetNode.rightNode != null||targetNode.leftNode != null) {
			RedBlackNode<K, V> nextNode = (targetNode.rightNode!=null)?targetNode.rightNode:targetNode.leftNode;
			if (targetNode.parentNode == null) {
				table[indexByNode(targetNode)] = nextNode;
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
			afterDelete(nextNode);
		}
		else {
			int index = indexByNode(targetNode);
			if (targetNode.parentNode == null) {
				targetNode = null;
				table[index] = null;
			}
			else if (targetNode == targetNode.parentNode.leftNode) {
				targetNode.parentNode.leftNode = null;
			}
			else {
				targetNode.parentNode.rightNode = null;
			}
			if (table[index] != null) {
				afterDelete(targetNode);
			}
		}
		
		
	}
	
	protected void afterRemoveForExchangeNodeInLinkedList(RedBlackNode<K, V> originalTargetNode, RedBlackNode<K, V> successorNode) {
		
	}
	
	protected void afterRemoveForLinked(RedBlackNode<K, V> targetNode) {
		
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
		RedBlackNode<K,V> rootNode = table[indexByNode(deletedNode)];

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
	
	protected RedBlackNode<K, V> createNode(K key, V value) {
		return new RedBlackNode<>(key, value);
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
	

}
