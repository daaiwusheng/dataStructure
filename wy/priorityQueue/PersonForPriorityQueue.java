package com.wy.priorityQueue;

public class PersonForPriorityQueue implements Comparable<PersonForPriorityQueue> {

	private String name;
	private int breakBones;
	
	
	
	public PersonForPriorityQueue(String name, int breakBones) {
		super();
		this.name = name;
		this.breakBones = breakBones;
	}

	@Override
	public int compareTo(PersonForPriorityQueue o) {
		return this.breakBones - o.breakBones ;
	}

	@Override
	public String toString() {
		return "PersonForPriorityQueue [name=" + name + ", breakBones=" + breakBones + "]";
	}
	
}
