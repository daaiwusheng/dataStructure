package com.wy.binaryTree;

import com.mj.printer.BinaryTrees;
import com.wy.testCases.Person;
import com.wy.testCases.TestCasesForAVLTree;
import com.wy.testCases.TestCasesForBinarySearchTree;
import com.wy.testCases.TestCasesForRedBlackTree;

public class MainForTree {

	public static void main(String[] args) {
		testBinarySearchTree();
//		testAVLTree();
//		testRBTree();
		
	}
     
	public static void testRBTree() {
//		TestCasesForRedBlackTree.TestCasesForAdd();
//		TestCasesForRedBlackTree.TestCasesForAddRandom();
		TestCasesForRedBlackTree.TestCasesForDelete();
	}
	
	public static void testAVLTree() {
//		TestCasesForAVLTree.TestCasesForAdd();
//		TestCasesForAVLTree.TestCasesForAddRandom();
//		TestCasesForAVLTree.TestCasesForDelete();
	}
	
	public static void testBinarySearchTree() {
//		TestCasesForBinarySearchTree.TestCasesForAdd();
		TestCasesForBinarySearchTree.testComparator();
//		TestCasesForBinarySearchTree.getCompletedTree();
//		TestCasesForBinarySearchTree.testRecoverTree();
//		TestCasesForBinarySearchTree.testFindPrecursorNode();
//		TestCasesForBinarySearchTree.testFindSuccessorNode();
//		TestCasesForBinarySearchTree.testDeleteElement();
//		TestCasesForBinarySearchTree.testReplaceElement();
		
//		TestCasesForBinarySearchTree.testPreorderTraversalNotByRecursion();
	}
	
	
}
