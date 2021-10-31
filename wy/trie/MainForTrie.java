package com.wy.trie;

import com.wy.testCases.Assert;

public class MainForTrie {

	public static void main(String[] args) {
		
		test1();
	}

	static void test1() {
		Trie<Integer> trie = new Trie<>();
		trie.add("cat", 1);
		
//		Assert.test(trie.remove("cat") == 1);
//		trie.add("dog", 2);
//		Assert.test(trie.size() == 1);
		
//		trie.add("dog", 2);
//		trie.add("catalog", 3);
//		trie.add("cast", 4);
//		trie.add("小码哥", 5);
//		
//		
//		
//		Assert.test(trie.size() == 5);
//		Assert.test(!trie.contains("ca"));
//		Assert.test(trie.startWith("do"));
//		Assert.test(trie.startWith("c"));
//		Assert.test(trie.startWith("ca"));
//		Assert.test(trie.startWith("cat"));
//		Assert.test(trie.startWith("cata"));
//		Assert.test(!trie.startWith("hehe"));
//		Assert.test(trie.get("小码哥") == 5);
//		Assert.test(trie.remove("ca") == null);
//		Assert.test(trie.remove("cat") == 1);
//		Assert.test(trie.remove("catalog") == 3);
//		Assert.test(trie.remove("cast") == 4);
//		Assert.test(trie.size() == 2);
//		Assert.test(trie.startWith("小"));
//		Assert.test(trie.startWith("do"));
//		Assert.test(!trie.startWith("c"));
	}
}
