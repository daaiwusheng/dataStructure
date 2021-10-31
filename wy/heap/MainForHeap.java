package com.wy.heap;

import java.util.ArrayList;
import java.util.Comparator;

import com.mj.printer.BinaryTrees;

public class MainForHeap {

	public static void main(String[] args) {
		
		testGetTopK();
				
		
		
	}

	public static void testGetTopK() {
		
		Integer[] eIntegers = {22, 40, 47, 58, 88, 24, 100, 53, 85, 51, 20, 91, 79, 21, 11, 41, 84};
		
		BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
			
				return o2.compareTo(o1);
			}
			
		});
		
		
		int topK = 15;
		
		for (int i = 0; i < eIntegers.length; i++) {
			if (heap.size() < topK) {
				heap.add(eIntegers[i]);
			}
			else if(heap.get() < eIntegers[i]) {
				heap.replace(eIntegers[i]);
			}
		}
		
		BinaryTrees.println(heap);
		
	}
	
	public static void testHeapify() {
		
		ArrayList<Integer> arrayList = new ArrayList<>();
		
		for (int i = 0; i < 1; i++) {
			int e = (int) (Math.random() * 100);
			arrayList.add(e);
		}
		
		Integer[] eIntegers = {22, 40, 47, 58, 88, 24, 100, 53, 85, 51, 20, 91, 79, 21, 11, 41, 84};
		
		BinaryHeap<Integer> heap = new BinaryHeap<>(eIntegers, null);
		
	
		BinaryTrees.println(heap);
		
		heap.travesal();
		
//		eIntegers[0] = 89;
		BinaryTrees.println(heap);
		
		
	}
	
	public static void testHeap() {
		BinaryHeap<Integer> heap = new BinaryHeap<>(null);
				
		heap.add(43);
		heap.add(68);
		heap.add(38);
		heap.add(72);
		heap.add(3);
		heap.add(40);
		
		for (int i = 0; i < 1; i++) {
			int e = (int) (Math.random() * 100);
			heap.add(e);
		}
		
		BinaryTrees.println(heap);
		
		heap.travesal();
		
		
		heap.replace(4);
		
		BinaryTrees.println(heap);
		
		heap.travesal();
		for (int i = 0; i <15; i++) {
			System.out.println("top is : "+heap.remove());	
			
			BinaryTrees.println(heap);
			
			heap.travesal();
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
