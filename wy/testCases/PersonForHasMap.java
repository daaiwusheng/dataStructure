package com.wy.testCases;

public class PersonForHasMap implements Comparable<PersonForHasMap> {

	int age;
	float height;
	String name;
		
	public PersonForHasMap(int age, float height, String name) {
		this.age = age;
		this.height = height;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (this == obj) {
			
			return true;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		PersonForHasMap pObj = (PersonForHasMap)obj;
		
		if (age == pObj.age 
			&& height == pObj.height 
			&& (name == null?pObj.name == null:name.equals(pObj.name))) {
			return true;
		}
		
		
		return false;
	}
	
	@Override
	public int hashCode() {
		
		int hashCode = Integer.hashCode(age);
		hashCode = hashCode * 31 + Float.hashCode(height);
		hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
		return hashCode;
	}
	
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(age+"_");
		stringBuilder.append(height+"_");
		stringBuilder.append(name);
		
		return stringBuilder.toString();
	}

	@Override
	public int compareTo(PersonForHasMap o) {
		
		return this.age - o.age;
	}
	

	
	
}
