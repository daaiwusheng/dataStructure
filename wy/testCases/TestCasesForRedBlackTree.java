package com.wy.testCases;

import java.util.ArrayList;
import java.util.Scanner;

import com.mj.printer.BinaryTrees;
import com.wy.binaryTree.AVLTree;
import com.wy.binaryTree.BinarySearchTree;
import com.wy.binaryTree.RedBlackTree;

public class TestCasesForRedBlackTree {

public static void TestCasesForAdd() {
		
		Integer[] dataForTree = new Integer[] {99};
		
		RedBlackTree<Integer> RBtree = new RedBlackTree<>();
		
		for (int i = 0; i < dataForTree.length; i++) {
			Integer data = dataForTree[i];
			RBtree.add(data);
		}

		System.out.println();
		System.out.println("---------------------"+RBtree.size());
		BinaryTrees.print(RBtree);
		
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
					System.out.println("---------------------"+RBtree.size());
					BinaryTrees.print(RBtree);
				}
	            else if (number<0) {
//	            	RBtree.deleteElement(dataForTree.get(Math.abs(number)));
					System.out.println();
					System.out.println();
					System.out.println("---------------------"+RBtree.size());
					BinaryTrees.print(RBtree);
				}
	            else {
		            RBtree.add(number);
					System.out.println();
					System.out.println();
					System.out.println("---------------------"+RBtree.size());
					BinaryTrees.print(RBtree);
				}
	            
	            
	        }
			
		}		
	}
		
		public static void TestCasesForAddRandom() {
			
			Integer[] data = new Integer[] {35, 83, 93, 36, 96, 88, 95, 98, 45};//};//
			//56,55,49
			
			ArrayList<Integer> dataForTree = new ArrayList<>();
			
			RedBlackTree<Integer> RBtree = new RedBlackTree<>();
			BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
			
//			int limit = data.length;
			int limit = 5;
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
				RBtree.add(d);
				System.out.println();
				System.out.println("---------------------"+RBtree.size());
				BinaryTrees.print(RBtree);
			}
			
			System.out.println();
			
		}
		
		public static void TestCasesForDelete() {
		
			Integer[] data = new Integer[] {29, 7, 39, 24, 59, 17, 23, 54, 75, 12, 96, 19, 68, 76, 38, 40, 98, 45, 84, 77};//
			//56,55,49
			
			ArrayList<Integer> dataForTree = new ArrayList<>();
			
			RedBlackTree<Integer> RBtree = new RedBlackTree<>();
			BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
			
//			int limit = data.length;
			int limit = 20;
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
				RBtree.add(d);
//				System.out.println();
//				System.out.println("---------------------"+avltree.size());
//				BinaryTrees.print(avltree);
			}
			
			System.out.println();
			System.out.println();
			System.out.println("---------------------"+RBtree.size());
			BinaryTrees.print(RBtree);
			
			
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
						System.out.println("---------------------"+RBtree.size());
						BinaryTrees.print(RBtree);
					}
		            else if (number<0) {
		            	RBtree.add(dataForTree.get(Math.abs(number)));
						System.out.println();
						System.out.println();
						System.out.println("---------------------"+RBtree.size());
						BinaryTrees.print(RBtree);
					}
		            else {
			            RBtree.deleteElement(number);
						System.out.println();
						System.out.println();
						System.out.println("---------------------"+RBtree.size());
						BinaryTrees.print(RBtree);
					}
		            
		            
		        }
				
			}
		
			
			
			
			
		}

	
	
}
