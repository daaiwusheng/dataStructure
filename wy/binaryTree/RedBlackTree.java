package com.wy.binaryTree;

import java.awt.Color;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import com.wy.binaryTree.BinaryTree.Node;
import com.wy.binaryTree.BinaryTree.Node.NodeChildType;
import com.wy.binaryTree.RedBlackTree.RedBlackNode.NodeColorType;

public class RedBlackTree<E> extends BalanceBinarySearchTree<E> {
	
	public static class RedBlackNode<E> extends Node<E> {

		NodeColorType color = NodeColorType.RED;
		
		public RedBlackNode(E element) {
			super(element);
		}
		
		private RedBlackNode<E> siblingNode() {
			
			NodeChildType type = judgeChildType();
			if (type == NodeChildType.Left) {
				return (RedBlackNode<E>)parentNode.rightNode;
			}
			else if (type == NodeChildType.Right) {
				return (RedBlackNode<E>)parentNode.leftNode;
			}
			else {
				return null;
			}
			
		}
		
		public static enum NodeColorType{
			RED,BLACK;
		}
		
		
	}
	
	@Override
	protected Node<E> creatNewNode(E element) {
		return new RedBlackNode(element);
	}
	
	@Override
	protected void afterReplaceByNewNode(Node<E> newNode, Node<E> oldNode) {
		//copy oldNode's color
		((RedBlackNode<E>)newNode).color = ((RedBlackNode<E>)oldNode).color;
	}
	
	@Override
	protected void afterAddNewNode(Node<E> node) {
		RedBlackNode<E> parentNode = (RedBlackNode<E>)node.parentNode;
		
		if (parentNode == null) {
			//it means node is rootNode, so color it as black
			black((RedBlackNode<E>)node);
			return;
		}
		
		if (isBlack(parentNode)) {
			//here, we do not need to do anything
			return;
		}
		
		RedBlackNode<E> uncleNode = parentNode.siblingNode();
		RedBlackNode<E> grandNode = (RedBlackNode<E>)parentNode.parentNode;
		
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
				black((RedBlackNode<E>)node);
				
				rotateToLeft(parentNode, node);
				rotateToRight(grandNode, node);
			}
		}
		else {//R
			if (currentNodeChildType == NodeChildType.Left) {//RL
				red(grandNode);
				black((RedBlackNode<E>)node);
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
	
	private NodeColorType colorOf(RedBlackNode<E> node) {
		return node == null?NodeColorType.BLACK:node.color;
	}
	
	private boolean isBlack(RedBlackNode<E> node) {
		return colorOf(node) == NodeColorType.BLACK;
	}
	
	private boolean isRed(RedBlackNode<E> node) {
		return colorOf(node) == NodeColorType.RED;
	}
	
	private RedBlackNode<E> color(RedBlackNode<E> node, NodeColorType color) {
		if (node != null) {
			node.color = color;
		}
		return node;
	}
	
	private RedBlackNode<E> black(RedBlackNode<E> node) {
		return color(node, NodeColorType.BLACK);
	}
	
	private RedBlackNode<E> red(RedBlackNode<E> node) {
		return color(node, NodeColorType.RED);
	}
	
	@Override
	protected void afterDelete(Node<E> deletedNode) {
		
		RedBlackNode<E> redBlackReplaceNode = (RedBlackNode<E>)deletedNode;
		if (isRed(redBlackReplaceNode)) {
			//1.父类传入的是用于替代被删除节点的节点,而删除的只可能是叶子节点,此时
			//如果这个节点用于替代其父节点,那么说明其父节点是黑色节点,并且度为1,此时染黑替代节点即可
			//2.如果此时传入的节点是真正要被删除的节点,说明它是要删除节点的前驱或者后继节点,染黑并无妨碍,因为
			//它已经不在树中了
			black(redBlackReplaceNode);
			return;
		}
		
		RedBlackNode<E> redBlackDeleteNode = (RedBlackNode<E>)deletedNode;
		
		if (rootNode == redBlackDeleteNode) {
			black(redBlackDeleteNode);
			return;
		}
		
		if (isBlack(redBlackDeleteNode)) {
			//被删除节点如果是黑色,那么它一定有兄弟节点,否则不会满足性质5,
			//而此时被删除节点已经为父节点删除了,所以siblingNode() 这个方法已经不好用了
			RedBlackNode<E> parentNode = (RedBlackNode<E>)redBlackDeleteNode.parentNode;
			RedBlackNode<E> siblingNode = redBlackDeleteNode.siblingNode();
			if (siblingNode == null ) {
				if (parentNode.leftNode != null) {
					siblingNode = (RedBlackNode<E>)parentNode.leftNode;
				}
				else {
					siblingNode = (RedBlackNode<E>)parentNode.rightNode;
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
					siblingNode = (RedBlackNode<E>)parentNode.leftNode;
				}
				else {
					rotateToLeft(parentNode, siblingNode);	
					siblingNode = (RedBlackNode<E>)parentNode.rightNode;
				}
				
			}
			
			//判断兄弟节点是否有红色子节点
			if (isRed((RedBlackNode<E>)siblingNode.leftNode)&&isRed((RedBlackNode<E>)siblingNode.rightNode)) {
				//兄弟节点有两个红色子节点,那么无论兄弟节点是左子节点还是右子节点,都可以只旋转一次
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Left) {
					//说明是LL的情况,那么父节点右旋转即可
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<E>)siblingNode.leftNode);
					black(parentNode);
					rotateToRight(parentNode, siblingNode);
				}
				else {
					//RR, 父节点向左旋转
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<E>)siblingNode.rightNode);
					black(parentNode);
					rotateToLeft(parentNode, siblingNode);
				}
			}
			else if (isRed((RedBlackNode<E>)siblingNode.leftNode)) {
				//兄弟节点只有左侧子节点是红色
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Left) {
					//说明是LL的情况,那么父节点右旋转即可
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<E>)siblingNode.leftNode);
					black(parentNode);
					rotateToRight(parentNode, siblingNode);
				}
				else {
					//RL, 先对sibling 向右旋转,然后再对parent 向左旋转
					color((RedBlackNode<E>)siblingNode.leftNode, colorOf(parentNode));
					black(parentNode);
					rotateToRight(siblingNode, siblingNode.leftNode);
					rotateToLeft(parentNode, parentNode.rightNode);
				}

			}
			else if (isRed((RedBlackNode<E>)siblingNode.rightNode)) {
				//兄弟节点只有右侧子节点是红色
				NodeChildType siblingChildType = siblingNode.judgeChildType();
				if (siblingChildType == NodeChildType.Right) {
					//RR, 父节点向左旋转
					color(siblingNode, colorOf(parentNode));
					black((RedBlackNode<E>)siblingNode.rightNode);
					black(parentNode);
					rotateToLeft(parentNode, siblingNode);
				}
				else {
					//说明是LR的情况,先对sibling进行左旋转,然后再对parent进行右旋转
					color((RedBlackNode<E>)siblingNode.rightNode, colorOf(parentNode));
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Object string(Object node) {
		
		RedBlackNode<E> redBlackNode = (RedBlackNode<E>)node;
		
		String string = "";
		if (isRed(redBlackNode)) {
			string = "R_";
		}
		
		return string + ((Node<E>)node).element;
	}
}































