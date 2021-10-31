package com.wy.heap;

import java.util.Comparator;

public abstract class AbstractHeap<E> implements Heap<E> {

	
	final static int DEFAULT_CAPACITY = 1<<4;
	protected Comparator<E> comparator;
	int size = 0;
	
	public AbstractHeap(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(E element) {
		// TODO Auto-generated method stub

	}

	@Override
	public E get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E replace(E element) {
		// TODO Auto-generated method stub
		return null;
	}

	protected int compare(E e1, E e2) {
		
		if (this.comparator != null) {
			return	comparator.compare(e1, e2);
		}
		else {
			return ((Comparable<E>)e1).compareTo(e2);
		}
		
	}
}
