package com.wy.testCases;


import java.util.ArrayList;
import java.util.Comparator;

import com.mj.printer.BinaryTrees;
import com.wy.binaryTree.BinarySearchTree;
import com.wy.tree.printerTool.BinaryTreePrinter;


public class TestCasesForBinarySearchTree {
	
	
	public static void testPreorderTraversalNotByRecursion() {
		
		Integer[] dataForTree = new Integer[] {7, 4, 9, 2, 5, 8, 11, 3, 12, 1};
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		for (int i = 0; i < dataForTree.length; i++) {
			Integer data = dataForTree[i];
			tree.add(data);
		}

		BinaryTrees.print(tree);
		System.out.println();
//		tree.preorderTraversalNotByRecursion();
//		tree.inorderTraversalNotByRecursion();
		tree.postorderTraversalNotByRecursion();
	}
	
	public static void TestCasesForAdd() {
		
		Integer[] dataForTree = new Integer[] {7, 4, 9, 2, 5, 8, 11, 3, 12, 1};
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		for (int i = 0; i < dataForTree.length; i++) {
			Integer data = dataForTree[i];
			tree.add(data);
		}

		BinaryTrees.print(tree);
	}
	
	
	private static class personComparator implements Comparator<Person>{

		public int compare(Person o1, Person o2) {
			
			return -o1.age + o2.age;
		}

	}
	
	public static void testComparator() {
		Integer[] dataForTree = new Integer[]{7, 4, 9, 2, 5, 8, 11, 3, 12, 1,20,30,13};
		
		//		{9999,120,500,60,50, 111, 31, 112, 11,100,44,25,9,99,2071, 49, 97, 21, 52, 18,900,12,18,233,799,96,95,94,93,92,91,43,42};
	
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 12; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		
			
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
		
//		BinarySearchTree<Person> tree = new BinarySearchTree<>(new personComparator());
		
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}
		
		
		
		
		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();
		
		
		tree.reverseTree();
		
		System.out.println();
		
		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();

		System.out.print(tree.isComplete());
		
//		tree.preorderTraversal(new Visitor<Person>() {
//	
//			public void visitElement(Person element) {
//				System.out.println(element);
//				if (element.age == 2) {
//					this.isStop = true;
//				}
//			}
//		});
//		
		
		
//		tree.inorderTraversal(new Visitor<Person>() {
//			public void visitElement(Person element) {
//				System.out.println(element);
//				if (element.age == 1) {
//					this.isStop = true;
//				}
//			}
//			
//		});
//		
		
		
//		tree.postorderTraversal(new Visitor<Person>() {
//			
//			public void visitElement(Person element) {
//				System.out.println(element);
//				if (element.age == 1) {
//					this.isStop = true;
//				}
//			}
//		});
//		tree.levelOrderTraversal(new Visitor<Person>() {
			
//			public void visitElement(Person element) {
//				System.out.print(element);
//				if (element.age == 4) {
//					this.isStop = true;
//				}
//			}
			
//		});
//		tree.levelOrderTraversalByQueue(new Visitor<Person>() {
//			public void visitElement(Person element) {
//				System.out.println(element);
//				if (element.age == 4) {
//					this.isStop = true;
//				}
//			}
//		});
		
	}
	
	public static void getCompletedTree() {
		
		while (true) {
			ArrayList<Integer> dataForTreeList = new ArrayList<>();
			
			for (int i = 0; i < 8; i++) {
				Integer rInteger = (int)(Math.random() * 100);
				dataForTreeList.add(rInteger);
				
			}
			
			ArrayList<Person> arrayListPersons = new ArrayList<>();
			for (int i = 0; i < dataForTreeList.size(); i++) {
				Person person = new Person(dataForTreeList.get(i), 0);
				arrayListPersons.add(person);
			}
						
			BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

				@Override
				public int compare(Person o1, Person o2) {
					
					return o1.age - o2.age;
				}
			
			});
			
			
			for (int i = 0; i < arrayListPersons.size(); i++) {
				tree.add(arrayListPersons.get(i));
			}
			
			if (tree.isComplete()) {
				BinaryTrees.println(tree);
				
				System.out.println();
				
				BinaryTreePrinter.printCurrentTree(tree);
				
				System.out.println();
				
				System.out.print(tree.isComplete());
				
				break;
				
				
			}
			
		}
	}
	
	
	public static void testRecoverTree() {
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 8; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
					
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}

		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();
		
		
		ArrayList<Person> arrayListPersonsPreorder = tree.getPreorderArrayList();
		ArrayList<Person> arrayListPersonsInorder = tree.getInorderArrayList();
		
		System.out.println(arrayListPersonsPreorder);
		
		System.out.println(arrayListPersonsInorder);
		
		
		BinarySearchTree<Person> treeCopy = tree.recoverBinaryTreeViaPreorderAndInorderList(arrayListPersonsPreorder, arrayListPersonsInorder);
		
		System.out.println();
		
		BinaryTrees.println(treeCopy);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(treeCopy);
		
		System.out.println();
		
	}
	
	public static void testFindPrecursorNode() {
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
					
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}

		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();
		
		Person person = arrayListPersons.get(1);
		
		System.out.println(person);
		
		System.out.println(tree.findPrecursorNode(person));
		
		
	}
	public static void testFindSuccessorNode() {
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
					
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}

		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();
		
		Person person = arrayListPersons.get(0);
		
		System.out.println(person);
		
		System.out.println(tree.findSuccessorNode(person));
		
		
	}
	public static void testDeleteElement() {
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
					
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}

		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		
		System.out.println();
		
		Person person = arrayListPersons.get(1);
		
		System.out.println(person);
		
		tree.deleteElement(person);
		
		System.out.println();
		BinaryTrees.println(tree);
		
		System.out.println();
		
		BinaryTreePrinter.printCurrentTree(tree);
		

		
		
	}
	
	public static void testReplaceElement() {
		ArrayList<Integer> dataForTreeList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Integer rInteger = (int)(Math.random() * 100);
			dataForTreeList.add(rInteger);
			
		}
		
		ArrayList<Person> arrayListPersons = new ArrayList<>();
		for (int i = 0; i < dataForTreeList.size(); i++) {
			Person person = new Person(dataForTreeList.get(i), 0);
			arrayListPersons.add(person);
		}
					
		BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {

			@Override
			public int compare(Person o1, Person o2) {
				
				return o1.age - o2.age;
			}
		
		});
		
		
		for (int i = 0; i < arrayListPersons.size(); i++) {
			tree.add(arrayListPersons.get(i));
		}

		BinaryTrees.println(tree);
		
		System.out.println();
		
		Person person = new Person(dataForTreeList.get(0), 100);
		
		tree.add(person);
		
		BinaryTrees.println(tree);
		
		System.out.println();
		

		
		
	}

	
	
}

























