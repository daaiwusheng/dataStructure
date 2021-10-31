package com.wy.set;

import com.wy.binaryTree.AVLTree;

import file.FileInfo;
import file.Files;
import file.Times;
import file.Times.Task;

public class MainForSet {


	
	public static void main(String[] args) {
		
		FileInfo fileInfo = 
				Files.read("/Users/wangyu/Desktop/数据结构和算法/src/java/util", new String[] {"java"});
		
		System.out.println("文件数 :"+ fileInfo.getFiles());
		System.out.println("行数 :"+ fileInfo.getLines());
		System.out.println("单词数 :"+ fileInfo.words().length);
		
		
		String[] wordStrings = {"11","23","44"};
		
		
		
//		Times.test("链表 实现的集合", new Task() {
//			
//			@Override
//			public void execute() {
//				
//				test(new ListSet<String>(), fileInfo.words());
//				
//			}
//		});
		
		Times.test("AVL树 实现的集合", new Task() {
			
			@Override
			public void execute() {
				
				test(new AVLTreeSet<String>(), fileInfo.words());
				
			}
		});
		
		
		Times.test("红黑树 实现的集合", new Task() {
			
			@Override
			public void execute() {
				
				test(new RedBlackTreeSet<String>(), fileInfo.words());
				
			}
		});
		
		
	}

	
	public static void test(Set<String> set, String[] words) {
		int size = words.length;
		for (int i = 0; i < size; i++) {
			set.add(words[i]);
			
		}
		System.out.println("size = " + set.size());
		
		for (int i = 0; i < size; i++) {
			set.contains(words[i]);
		}
		
		for (int i = 0; i < size; i++) {
			set.remove(words[i]);;
		}
		
	}
}
