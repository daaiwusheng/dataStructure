package com.wy.testCases;

import com.wy.map.Map;
import com.wy.map.Map.Visitor;
import com.wy.set.AVLTreeSet;

import file.FileInfo;
import file.Files;
import file.Times;
import file.Times.Task;

import com.wy.map.TreeMap;

public class TestCasesForTreeMap {

	
	public static void testAddMethod() {
		
		Map<String, Integer> map = new TreeMap<>(null);
		
		map.put("hello", 10);
		map.put("hello", 11);
		map.put("hello", 10);
		map.put("yes", 12);
		map.put("but", 13);
		map.put("cry", 99);
		
		
		map.traversal(new Visitor<String, Integer>() {
			
			@Override
			public boolean visit(String key, Integer value) {
				
				System.out.println("key: "+key +"  "+ "value: " + value);
				return false;
			}
		});
		
		map.remove("hello");
	
		System.out.println("----------------------");
		
		System.out.println(map.containsKey("helllo"));
		
		System.out.println(map.containsKey("cry"));
		
		System.out.println(map.containsValue(99));
		
		map.traversal(new Visitor<String, Integer>() {
			
			@Override
			public boolean visit(String key, Integer value) {
				
				System.out.println("key: "+key +"  "+ "value: " + value);
				return false;
			}
		});
		
	}
	
	public static void testGetMethod() {
		
		Map<String, Integer> map = new TreeMap<>(null);
		
		map.put("hello", 10);
		map.put("hello", 11);
		map.put("hello", 10);
		map.put("yes", 12);
		map.put("but", 13);
		map.put("cry", 99);
		
		Integer	vInteger = map.get("but");
	
		
		System.out.print(vInteger);
		
	}

	public static void testTimes() {
		FileInfo fileInfo = 
				Files.read("/Users/wangyu/Desktop/数据结构和算法/src/java/util/logging", new String[] {"java"});
		
		System.out.println("文件数 :"+ fileInfo.getFiles());
		System.out.println("行数 :"+ fileInfo.getLines());
		System.out.println("单词数 :"+ fileInfo.words().length);
		
		String[] words = fileInfo.words();
		Times.test("字典,用黑叔实现:", new Task() {
			
			@Override
			public void execute() {
				
				Map<String, Integer> map = new TreeMap<>(null);
				int sizeWords = words.length;
				for (int i = 0; i < sizeWords; i++) {
					
					int count = map.get(words[i]) == null?0:map.get(words[i]);
					
					map.put(words[i], count+1);
					
				}
				
				map.traversal(new Visitor<String, Integer>() {
					
					@Override
					public boolean visit(String key, Integer value) {
						
						System.out.println("key: "+key +"  "+ "value: " + value);
						return false;
					}
				});

				
			}
		});

	}
	
}
