package com.wy.testCases;


public class Person implements Comparable<Person> {
	int age;
	int height;
	
	public Person(int age, int height) {
		super();
		this.age = age;
		this.height = height;
	}

	@Override
	public int compareTo(Person personLater) {
		
		return this.age - personLater.age;
	}

	
	@Override
	public String toString() {
		
		StringBuilder stringResult = new StringBuilder();
		stringResult.append("p_"+age+"_h="+height);
		return stringResult.toString();
	}
	
}
