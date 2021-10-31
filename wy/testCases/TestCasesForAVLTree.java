package com.wy.testCases;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

import com.mj.printer.BinaryTrees;
import com.wy.binaryTree.AVLTree;
import com.wy.binaryTree.BinarySearchTree;
import com.wy.tree.printerTool.BinaryTreePrinter;

public class TestCasesForAVLTree {
		public static void TestCasesForAdd() {
		
		Integer[] dataForTree = new Integer[] {7, 4, 9, 2, 5, 8, 15, 3, 99, 1};
		
		AVLTree<Integer> tree = new AVLTree<>();
		
		for (int i = 0; i < dataForTree.length; i++) {
			Integer data = dataForTree[i];
			tree.add(data);
		}

		tree.add(6);
		System.out.println();
		System.out.println();
		BinaryTrees.print(tree);
		tree.add(88);
		
		System.out.println();
		System.out.println();
		BinaryTrees.print(tree);
		tree.add(77);
		
		System.out.println();
		System.out.println();
		
		BinaryTrees.print(tree);
		
		tree.add(55);
		
		System.out.println();
		System.out.println();
		
		BinaryTrees.print(tree);
		
		tree.add(66);
		
		System.out.println();
		System.out.println();
		
		BinaryTrees.print(tree);
		
		tree.add(44);
		
		System.out.println();
		System.out.println();
		
		BinaryTrees.print(tree);
		
	}
		
		public static void TestCasesForAddRandom() {
			
			Integer[] data = new Integer[] {35, 83, 93, 36, 96, 88, 95, 98, 45};//};//
			//56,55,49
			
			ArrayList<Integer> dataForTree = new ArrayList<>();
			
			AVLTree<Integer> avltree = new AVLTree<>();
			BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
			
//			int limit = data.length;
			int limit = 15;
			for (int i = 0; i < limit; i++) {
				int d = (int)(Math.random()*100);
//				int d = data[i];
				dataForTree.add(d);
				binarySearchTree.add(d);
			}
			
			
			System.out.print(dataForTree);
			System.out.println();
			System.out.println();
			
			System.out.println("---"+binarySearchTree.size());
			
			BinaryTrees.print(binarySearchTree);
			
			for (int i = 0; i < dataForTree.size(); i++) {
				int d = dataForTree.get(i);
				avltree.add(d);
				System.out.println();
				System.out.println("---------------------"+avltree.size());
				BinaryTrees.print(avltree);
			}
			
			System.out.println();
			
		}
		
		public static void TestCasesForDelete() {
		
			Integer[] data = new Integer[] {17,5, 7, 51, 23, 22, 25, 83, 61, 85};//
			//56,55,49
			
			ArrayList<Integer> dataForTree = new ArrayList<>();
			
			AVLTree<Integer> avltree = new AVLTree<>();
			BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
			
//			int limit = data.length;
			int limit = 15;
			for (int i = 0; i < limit; i++) {
				int d = (int)(Math.random()*100);
//				int d = data[i];
				dataForTree.add(d);
				binarySearchTree.add(d);
			}
			
			
			System.out.print(dataForTree);
			System.out.println();
			System.out.println();
			
			System.out.println("---------------------"+binarySearchTree.size());
			
			BinaryTrees.print(binarySearchTree);
			
			for (int i = 0; i < dataForTree.size(); i++) {
				int d = dataForTree.get(i);
				avltree.add(d);
//				System.out.println();
//				System.out.println("---------------------"+avltree.size());
//				BinaryTrees.print(avltree);
			}
			
			System.out.println();
			System.out.println();
			System.out.println("---------------------"+avltree.size());
			BinaryTrees.print(avltree);
			
			
			Scanner scan = new Scanner(System.in);
	        // 从键盘接收数据    
	        while (true) {
	        	// next方式接收字符串
	        	System.out.println();
		        System.out.println("next方式接收：");
		        // 判断是否还有输入
		        if (scan.hasNext()) {
		            int number = scan.nextInt();
		            System.out.println("输入的数据为：" + number);
		            if (number == -99) {
		            	System.out.println();
						System.out.println("---------------------"+avltree.size());
						BinaryTrees.print(avltree);
					}
		            else if (number<0) {
		            	avltree.deleteElement(dataForTree.get(Math.abs(number)));
						System.out.println();
						System.out.println();
						System.out.println("---------------------"+avltree.size());
						BinaryTrees.print(avltree);
					}
		            else {
			            avltree.deleteElement(number);
						System.out.println();
						System.out.println();
						System.out.println("---------------------"+avltree.size());
						BinaryTrees.print(avltree);
					}
		            
		            
		        }
				
			}
		
			
			
			
			
		}
}


























