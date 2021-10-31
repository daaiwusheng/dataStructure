package com.wy.hashMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import file.FileInfo;
import file.Files;
import file.Times;
import file.Times.Task;

import com.wy.map.Map.Visitor;
//import com.wy.map.Map;
import com.wy.testCases.Assert;
import com.wy.testCases.Key;
import com.wy.testCases.PersonForHasMap;
import com.wy.testCases.SubKey1;
import com.wy.testCases.SubKey2;


public class MainForHashMap {

	static void test6(HashMap<Object, Integer> map) {

		for (int i = 1; i <= 20; i++) {
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			Assert.test(map.remove(new Key(i)) == i);
		}

		Assert.test(map.size() == 17);
		map.traversal(new Visitor<Object, Integer>() {
			public boolean visit(Object key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}

	public static void main(String[] args) {
		
//		test2(new HashMap<>());
//		test3(new HashMap<>());
//		test4(new HashMap<>());
		
//		test5(new HashMap<>());
//		test1();
		
//		test6(new HashMap<>());
		
		test2(new com.wy.hashMap.LinkedHashMap());
		test3(new com.wy.hashMap.LinkedHashMap());
		test4(new com.wy.hashMap.LinkedHashMap());
		test5(new com.wy.hashMap.LinkedHashMap());
		test6(new com.wy.hashMap.LinkedHashMap());
//		HashMap<Object, Object> hashMap = new HashMap<>();
//		for (int i = 0; i < 15; i++) {
//			hashMap.put(new Key(i), i);
//		}
//		System.out.println(hashMap.get(new Key(5)));
//		
//		PersonForHasMap p_1 = new PersonForHasMap(10, 1.70f, "Jack");
//		
//		hashMap.put(p_1, 99);
//		
//		PersonForHasMap p_2 = new PersonForHasMap(19, 1.70f, "Jack");
//		
//		hashMap.put(p_2, null);
//		hashMap.put(null, null);
//		
//		
//		System.out.println(hashMap.get(p_2));
//		
//		hashMap.traversal(null);
		
	
		
		
		
		
	}
	static void test1Map(com.wy.map.Map<String, Integer> map, String[] words) {
		Times.test(map.getClass().getName(), new Task() {
			@Override
			public void execute() {
				for (String word : words) {
					Integer count = map.get(word);
					count = count == null ? 0 : count;
					map.put(word, count + 1);
				}
				System.out.println(map.size()); // 17188
				
				int count = 0;
				for (String word : words) {
					Integer i = map.get(word);
					count += i == null ? 0 : i;
					map.remove(word);
				}
				Assert.test(count == words.length);
				Assert.test(map.size() == 0);
				System.out.println();
			}
		});
	}
	
	
	static void test2Map(java.util.Map<String, Integer> map, String[] words) {
		Times.test(map.getClass().getName(), new Task() {
			@Override
			public void execute() {
				for (String word : words) {
					Integer count = map.get(word);
					count = count == null ? 0 : count;
					map.put(word, count + 1);
				}
				System.out.println(map.size()); // 17188
				
				int count = 0;
				for (String word : words) {
					Integer i = map.get(word);
					count += i == null ? 0 : i;
					map.remove(word);
				}
				Assert.test(count == words.length);
				Assert.test(map.size() == 0);
				System.out.println();
			}
		});
	}
	
	static void test1() {
		String filepath = "/Users/wangyu/Desktop/数据结构和算法/src/java/util";
		FileInfo fileInfo = Files.read(filepath, null);
		String[] words = fileInfo.words();

		System.out.println("总行数：" + fileInfo.getLines());
		System.out.println("单词总数：" + words.length);
		System.out.println("-------------------------------------");

		test2Map(new TreeMap<>(), words);
		test1Map(new HashMap<>(), words);
		test2Map(new LinkedHashMap<>(), words);
	}
	
	static void test2(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			map.put(new Key(i), i + 5);
		}
		Assert.test(map.size() == 20);
		Assert.test(map.get(new Key(4)) == 4);
		Assert.test(map.get(new Key(5)) == 10);
		Assert.test(map.get(new Key(6)) == 11);
		Assert.test(map.get(new Key(7)) == 12);
		Assert.test(map.get(new Key(8)) == 8);
	}
	
	static void test3(HashMap<Object, Integer> map) {
		map.put(null, 1); // 1
		map.put(new Object(), 2); // 2
		map.put("jack", 3); // 3
		map.put(10, 4); // 4
		map.put(new Object(), 5); // 5
		map.put("jack", 6);
		map.put(10, 7);
		map.put(null, 8);
		map.put(10, null);
		Assert.test(map.size() == 5);
		Assert.test(map.get(null) == 8);
		Assert.test(map.get("jack") == 6);
		Assert.test(map.get(10) == null);
		Assert.test(map.get(new Object()) == null);
		Assert.test(map.containsKey(10));
		Assert.test(map.containsKey(null));
		Assert.test(map.containsValue(null));
		Assert.test(map.containsValue(1) == false);
	}
	
	static void test4(HashMap<Object, Integer> map) {
		map.put("jack", 1);
		map.put("rose", 2);
		map.put("jim", 3);
		map.put("jake", 4);		
		map.remove("jack");
		map.remove("jim");
		for (int i = 1; i <= 10; i++) {
			map.put("test" + i, i);
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			Assert.test(map.remove(new Key(i)) == i);
		}
		for (int i = 1; i <= 3; i++) {
			map.put(new Key(i), i + 5);
		}
		Assert.test(map.size() == 19);
		Assert.test(map.get(new Key(1)) == 6);
		Assert.test(map.get(new Key(2)) == 7);
		Assert.test(map.get(new Key(3)) == 8);
		Assert.test(map.get(new Key(4)) == 4);
		Assert.test(map.get(new Key(5)) == null);
		Assert.test(map.get(new Key(6)) == null);
		Assert.test(map.get(new Key(7)) == null);
		Assert.test(map.get(new Key(8)) == 8);
		
		map.traversal(new Visitor<Object, Integer>() {
			public boolean visit(Object key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}
	
	static void test5(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new SubKey1(i), i);
		}
		map.put(new SubKey2(1), 5);
		Assert.test(map.get(new SubKey1(1)) == 5);
		Assert.test(map.get(new SubKey2(1)) == 5);
		Assert.test(map.size() == 20);
		
//		map.traversal(null);
	}
}












