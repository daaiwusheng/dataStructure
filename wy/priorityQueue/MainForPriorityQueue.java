package com.wy.priorityQueue;

public class MainForPriorityQueue {

	public static void main(String[] args) {
		
		PriorityQueue<PersonForPriorityQueue> queue = new PriorityQueue<>();
		
		queue.add(new PersonForPriorityQueue("J", 10));
		
		queue.add(new PersonForPriorityQueue("Caroline", 20));
		
		queue.add(new PersonForPriorityQueue("Ashley", 2));
		
		queue.add(new PersonForPriorityQueue("Ellie", 8));
		
		for (int i = 0; i < 4; i++) {
			System.out.println(queue.poll());
		}

	}

}
