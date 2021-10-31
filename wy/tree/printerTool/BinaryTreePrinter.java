package com.wy.tree.printerTool;

import java.awt.Canvas;
import java.util.ArrayList;

import javax.security.auth.kerberos.KerberosCredMessage;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.wy.binaryTree.BinaryTree.Visitor;

public class BinaryTreePrinter {
	
	WYBinaryTreeInfo treeInfo;
	ArrayList<ArrayList<Node>> arrayListOfNodesInLevel;
	int levelOfMostLeftNode;
	int heightBetweenNodes = 1;
	int minWidthBetweenNodes = 2;

	public static void printCurrentTree(WYBinaryTreeInfo treeInfo) {
		
		BinaryTreePrinter printer = new BinaryTreePrinter();
		printer.printTree(treeInfo);
		
	}
	
	private void printTree(WYBinaryTreeInfo treeInfo) {
		this.treeInfo = treeInfo;
		arrayListOfNodesInLevel = new ArrayList<>();
		consturctTree();
	}

	private void consturctTree() {
		
		Node rootNode = new Node();
		rootNode.element = treeInfo.root();
		rootNode.nodeType = NodeType.Root;
		
		if (rootNode.element == null) {
			System.out.println("空树");
			return;
		}
		
		//1.先复制一份外界的二叉树
		preorderCopy(rootNode, treeInfo.root());
		//2.计算每个节点的高度
		calculateHeightAndStoreByLevels(rootNode);
		//3.标记出最左侧的子节点,整个树的最左侧
		markLeftEdgeNodes(rootNode);
		//4.后续遍历,并且计算每个字数的宽度
		postorderCalculateWidth(rootNode);
		//5.终须遍历,计算每个节点的x值
		inorderCalculateCoordinateX(rootNode);
		
		//输出tree
//		printTree(rootNode);
		drawTree(rootNode);
//		levelOrder(rootNode);
		
	}
	/***
	 * 前序复制一份外界的二叉树,主要在于扩充每个节点的属性
	 * @param rootNodeNew
	 * @param objectNode
	 */
	private void preorderCopy(Node rootNodeNew,Object objectNode)
	{
		if (objectNode == null) {
			return;
		}
		
		copyTree(rootNodeNew, objectNode);
		
		preorderCopy(rootNodeNew.leftNode, treeInfo.left(objectNode));
		
		preorderCopy(rootNodeNew.rightNode, treeInfo.right(objectNode));
		
	}
	
	/**
	 * 复制每个节点的左右子节点
	 * @param rootNodeNew
	 * @param rootNodeOld
	 */
	private void copyTree(Node rootNodeNew,Object rootNodeOld) {
				
		if (treeInfo.left(rootNodeOld) != null) {
			rootNodeNew.leftNode = new Node();
			rootNodeNew.leftNode.element = treeInfo.left(rootNodeOld);
			rootNodeNew.leftNode.nodeType = NodeType.Left;
			rootNodeNew.leftNode.parentNode = rootNodeNew;
		}
		if (treeInfo.right(rootNodeOld) != null) {
			rootNodeNew.rightNode = new Node();
			rootNodeNew.rightNode.element = treeInfo.right(rootNodeOld);
			rootNodeNew.rightNode.nodeType = NodeType.Right;
			rootNodeNew.rightNode.parentNode = rootNodeNew;
		}
	
	}
	
	/**
	 * 计算每个节点的高度,也就是层数,并且分层存储节点
	 * @param rootNode
	 */
	private void calculateHeightAndStoreByLevels(Node rootNode) {
		
		ArrayList<Node> arrayListCurrentLevel = new ArrayList<>();
		arrayListCurrentLevel.add(rootNode);
		ArrayList<Node> arrayListNextLevel = new ArrayList<>();
	
		int level = 0;
		
		while (arrayListCurrentLevel.size()>0) {
			
			for (int i = 0; i < arrayListCurrentLevel.size(); i++) {
				Node nodeInSameLevel = arrayListCurrentLevel.get(i);
				nodeInSameLevel.level = level;
				nodeInSameLevel.y = level * heightBetweenNodes;
				if (nodeInSameLevel.leftNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.leftNode);
				}
				if (nodeInSameLevel.rightNode != null) {
					arrayListNextLevel.add(nodeInSameLevel.rightNode);
				}
				
			}
			level ++;
			
			ArrayList<Node> arrayListCopyCurrentLeveList = new ArrayList<>(arrayListCurrentLevel);
			arrayListOfNodesInLevel.add(arrayListCopyCurrentLeveList);
			
			arrayListCurrentLevel.clear();
			arrayListCurrentLevel.addAll(arrayListNextLevel);
			arrayListNextLevel.clear();
			
			
		}
		
		
	}
	/**
	 * 标记最左侧子节点,用于后续计算坐标使用
	 * @param rootNode
	 */
	private void markLeftEdgeNodes(Node rootNode) {
		if (rootNode == null) {
			return;
		}
		rootNode.isOnLeftEdge = true;
		markLeftEdgeNodes(rootNode.leftNode);
	}
	
	/**
	 * 节点类型,这样可以直接方便查看,当前节点是左右,还是根节点
	 * @author wangyu
	 *
	 */
	enum NodeType {
		Left,Right,Root;
	}
	/**
	 * 后续遍历计算每个子树的宽度,每个节点的宽度=左子数的宽度+ 右子树的宽度,加上自己的长度
	 * @param rootNode
	 */
	private void postorderCalculateWidth(Node rootNode) {
		
		if (rootNode == null) {
			return;
		}
		postorderCalculateWidth(rootNode.leftNode);
		postorderCalculateWidth(rootNode.rightNode);
		
		if (rootNode.leftNode!=null) {
			if (rootNode.leftNode.width>0) {
				rootNode.width += rootNode.leftNode.width;
			}
			else {
				rootNode.width += getNodeLength(rootNode.leftNode);
			}
			
		}
		
		if (rootNode.rightNode!=null) {
			if (rootNode.rightNode.width>0) {
				rootNode.width += rootNode.rightNode.width;
			}
			else {
				rootNode.width += getNodeLength(rootNode.rightNode);
			}
		}
		rootNode.width += getNodeLength(rootNode);
	}
	/**
	 * 中序遍历,计算每个节点的x值
	 * @param rootNode
	 */
	private void inorderCalculateCoordinateX(Node rootNode) {
		if (rootNode == null) {
			return;
		}
		inorderCalculateCoordinateX(rootNode.leftNode);
		if (rootNode.nodeType != NodeType.Right) {
			if (rootNode.isOnLeftEdge) {
				if (rootNode.leftNode != null) {
					
					if (rootNode.leftNode.rightNode != null) {
						
						if(rootNode.leftNode.rightNode.width>0) {
							rootNode.x = rootNode.leftNode.x + rootNode.leftNode.rightNode.width + minWidthBetweenNodes;
						}
						else {
							rootNode.x = rootNode.leftNode.rightNode.x + getNodeLength(rootNode.leftNode.rightNode)+ minWidthBetweenNodes;
						}
						
						
					}
					else {
						rootNode.x = rootNode.leftNode.x + getNodeLength(rootNode.leftNode)+ minWidthBetweenNodes;
					}
					
				}//if (rootNode.leftNode != null)
				else {
					rootNode.x = 0;
				}
			}//if (rootNode.isOnLeftEdge)
			else {
				
				if (rootNode.leftNode != null) {
					
					if (rootNode.leftNode.rightNode != null) {
						if(rootNode.leftNode.rightNode.width>0) {
							rootNode.x = rootNode.leftNode.x + rootNode.leftNode.rightNode.width+ minWidthBetweenNodes;
						}
						else {
							rootNode.x = rootNode.leftNode.rightNode.x + getNodeLength(rootNode.leftNode.rightNode)+ minWidthBetweenNodes;
						}

					}
					else {
						rootNode.x = rootNode.leftNode.x + getNodeLength(rootNode.leftNode)+ minWidthBetweenNodes;
					}
				}
				else {
					Node firstParentNodeHasX = findMostBigX(rootNode);
					rootNode.x = firstParentNodeHasX.x + getNodeLength(firstParentNodeHasX) + minWidthBetweenNodes;
				}
				
			}
		}//if (rootNode.nodeType != NodeType.Right)
		else {
			//此时这里是右子树,
			if (rootNode.leftNode!=null) {
				rootNode.x = rootNode.leftNode.x + rootNode.leftNode.width;
			}
			else {
				Node firstParentNodeHasX = findMostBigX(rootNode);
				rootNode.x = firstParentNodeHasX.x + getNodeLength(firstParentNodeHasX) + minWidthBetweenNodes;
			}
			
		}
		inorderCalculateCoordinateX(rootNode.rightNode);	
	}

	/**
	 * 获取当前节点的宽度,所以外界必须返回字符串类型
	 * @param currentNode
	 * @return
	 */
	private int getNodeLength(Node currentNode) {
		return ((String)treeInfo.string(currentNode.element)).length();
	}
	/**
	 * 找到最大可以依赖的x值,避免节点重叠
	 * @param currentNode
	 * @return
	 */
	private Node findMostBigX(Node currentNode) {
		
		Node nodeForSearchParent = currentNode;
		
		while (nodeForSearchParent!=null) {
			if (nodeForSearchParent.isOnLeftEdge || nodeForSearchParent.x>0) {
				break;
			}
			nodeForSearchParent = nodeForSearchParent.parentNode;
		}
		Node nodeForSearchMostRight = nodeForSearchParent;
		if (nodeForSearchMostRight.leftNode!=null) {
			
			nodeForSearchMostRight = findMostRightNode(nodeForSearchMostRight.leftNode);
			
		}
		if ((nodeForSearchMostRight.x+getNodeLength(nodeForSearchMostRight)) > 
			(nodeForSearchParent.x + getNodeLength(nodeForSearchParent))
				) {
			return nodeForSearchMostRight;
		}
		else {
			return nodeForSearchParent;
		}
	}
	
	/**
	 * 找到最右侧子节点
	 * @param rootNode
	 * @return
	 */
	private Node findMostRightNode(Node rootNode) {
		
		while (rootNode.rightNode != null) {
			rootNode = rootNode.rightNode;
		}
		
		return rootNode;
		
	}
	
	private static class Node{
		Object element;
		Node leftNode;
		Node rightNode;
		Node parentNode;
		NodeType nodeType;
		int x;
		int y;
		int level;	
		int width;
		boolean isOnLeftEdge;
		int rightArmForPrintLength;
		@Override
		public String toString() {
			
			return element.toString();
		}
		
	}
	
	static final String LEFT_INDICATOR = "┌";
	static final String RIGHT_INDICATOR = "┐";
	static final String LEVELLINE_INICATOR = "─";
	static final int MINSpaceLength = 0;
	static final String Space = " ";
	
	private void drawTree(Node rootNode) {
		
		for (int i = 0; i < arrayListOfNodesInLevel.size(); i++) {
			
			//输出y
			if (i > 0) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int k = 0; k < heightBetweenNodes; k++) {
					 stringBuilder.append("\n");
				}
				System.out.print(stringBuilder);
			}
			
			ArrayList<Node> arrayCurrentLevel = arrayListOfNodesInLevel.get(i);
			for (int j = 0; j < arrayCurrentLevel.size(); j++) {
				Node currentNode = arrayCurrentLevel.get(j);

				//输出x 和节点
				if (j == 0) {
					StringBuilder stringBuilder = new StringBuilder();
					if (currentNode.leftNode != null) {
						//比左子节点x值小的部分都是拼接空过,
						//左子节点x值开始拼接 "┌"
						int lengthOfSpace = currentNode.leftNode.x;
						
						for (int k = 0; k < lengthOfSpace; k++) {
							stringBuilder.append(Space);
						}
						stringBuilder.append(LEFT_INDICATOR);
						int lengthOfLevelLine = 
								currentNode.x 
								- currentNode.leftNode.x
								- LEFT_INDICATOR.length()
								;
						
						for (int k = 0; k < lengthOfLevelLine; k++) {
							stringBuilder.append(LEVELLINE_INICATOR);
						}
					}//if (currentNode.leftNode != null)
					else {
						int lengthOfSpace = currentNode.x;
						
						for (int k = 0; k < lengthOfSpace; k++) {
							stringBuilder.append(Space);
						}
					}
			        //把当前节点拼接上
					stringBuilder.append(currentNode.element);
					if (currentNode.rightNode!=null) {
						int lengthOfLevelLine = 
								currentNode.rightNode.x + 1
								- currentNode.x 
								- RIGHT_INDICATOR.length() 
								- getNodeLength(currentNode);
						if (lengthOfLevelLine <= 0) {
							lengthOfLevelLine = MINSpaceLength;
						}
						for (int k = 0; k < lengthOfLevelLine; k++) {
							stringBuilder.append(LEVELLINE_INICATOR);
						}
						
						stringBuilder.append(RIGHT_INDICATOR);
						currentNode.rightArmForPrintLength = lengthOfLevelLine + RIGHT_INDICATOR.length();
					}
					System.out.print(stringBuilder);
					
				}//if (j == 0)
				else {
					//如果当前节点的前面一个节点有右子树,那么从右子树的 输出的最右端的x值x_mostRightPoint
					//到 1.如果当前节点有左子树,那么到左子树的x值减去一半左子树字符的长度,的位置,是空格
					//然后拼接左子树标记,然后从这个标记开始到当前节点x值 都是中划线
					//2.如果当前节点没有左子树,那么前一个节点的最有端点值,到当前节点的x值,都是空格
					//然后考虑当前节点是否有右子树
					StringBuilder stringBuilder = new StringBuilder();
					Node previousNode = arrayCurrentLevel.get(j-1);
					if (currentNode.leftNode!=null) {
						int limitForPrintSpace = 
								currentNode.leftNode.x 
								- previousNode.x 
								-getNodeLength(previousNode)
								- previousNode.rightArmForPrintLength
								;
						for (int k = 0; k < limitForPrintSpace; k++) {
							stringBuilder.append(Space);
						}
						stringBuilder.append(LEFT_INDICATOR);
						//拼接中划线
						int limitForPrintLevelLine = 
								currentNode.x 
								- currentNode.leftNode.x
								- LEFT_INDICATOR.length() 
								;
						for (int k = 0; k < limitForPrintLevelLine; k++) {
							stringBuilder.append(LEVELLINE_INICATOR);
						}
						
					}//if (currentNode.leftNode!=null)
					else {
						int limitForPrintSpace = 
								currentNode.x  
								- previousNode.x  
								- (getNodeLength(previousNode))  
								- previousNode.rightArmForPrintLength
								;
						if (limitForPrintSpace <= 0) {
							limitForPrintSpace = MINSpaceLength;
						}
						for (int k = 0; k < limitForPrintSpace; k++) {
							stringBuilder.append(Space);
						}
						
					}
					//把当前节点拼接上
					stringBuilder.append(currentNode.element);
					//再看有无右子树
					if (currentNode.rightNode!=null) {
						int lengthOfLevelLine = 
								currentNode.rightNode.x -1
								- currentNode.x 
								- RIGHT_INDICATOR.length()
								;
						
						for (int k = 0; k < lengthOfLevelLine; k++) {
							stringBuilder.append(LEVELLINE_INICATOR);
						}
						
						stringBuilder.append(RIGHT_INDICATOR);
						currentNode.rightArmForPrintLength = 
								lengthOfLevelLine 
								+ RIGHT_INDICATOR.length();
					}
		
					System.out.print(stringBuilder);
						
				}
			}
		}
		System.out.println();
	}
	
	
	
	
	/**
	 * 打印当前二叉树,测试用,打印算完坐标的原始节点位置,没有加指示线
	 * @param rootNode
	 */
	private void printTree(Node rootNode) {
	
	for (int i = 0; i < arrayListOfNodesInLevel.size(); i++) {
		
		//输出y
		if (i > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int k = 0; k < heightBetweenNodes; k++) {
				 stringBuilder.append("\n");
			}
			System.out.print(stringBuilder);
		}
		
		ArrayList<Node> arrayCurrentLevel = arrayListOfNodesInLevel.get(i);
		for (int j = 0; j < arrayCurrentLevel.size(); j++) {
			Node currentNode = arrayCurrentLevel.get(j);

			//输出x 和节点
			if (j == 0) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int k = 0; k < currentNode.x; k++) {
					stringBuilder.append(Space);
				}
				stringBuilder.append(currentNode.element);
				System.out.print(stringBuilder);
			}
			else {
				StringBuilder stringBuilder = new StringBuilder();
				Node previousNode = arrayCurrentLevel.get(j-1);
				int limit = currentNode.x - previousNode.x - getNodeLength(previousNode);
				for (int k = 0; k < limit; k++) {
					stringBuilder.append(Space);
				}
				stringBuilder.append(currentNode.element);
				System.out.print(stringBuilder);
			}
		}
	}
	
		System.out.println();
	}
	/***
	 * 分层打印,测试用,会打印出每个节点的坐标,以及宽度
	 * @param rootNode
	 */
	private void levelOrder(Node rootNode) {
		System.out.println();
	
		if (rootNode == null) {
			return;
		}
		
		ArrayList<Node> arrayList = new ArrayList<>();
		arrayList.add(rootNode);
		ArrayList<Node> arrayListNextLevel = new ArrayList<>();
		
		int countLevel = 0;
		
		while (arrayList.size()>0) {

			System.out.print("level:="+ countLevel+":=  ");
			countLevel ++;
			for (int i = 0; i < arrayList.size(); i++) {
				Node nodeInSameLevel = arrayList.get(i);
				
				System.out.print(nodeInSameLevel+"x:"+ nodeInSameLevel.x+" w="+nodeInSameLevel.width);
				
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

	
}
























