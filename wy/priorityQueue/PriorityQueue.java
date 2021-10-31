package com.wy.priorityQueue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

import com.wy.heap.BinaryHeap;

public class PriorityQueue<E> implements Queue<E> {

	private	BinaryHeap<E> heap;
	
	public PriorityQueue() {
		this(null);
	}

public PriorityQueue(Comparator<E> comparator) {
		heap = new BinaryHeap<>();
	}
	
	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		heap.clear();
	}

	@Override
	public boolean add(E e) {
		int oldSize = heap.size();
		heap.add(e);
		int newSize = heap.size();
		return newSize > oldSize;
	}

	@Override
	public boolean offer(E e) {
		int oldSize = heap.size();
		heap.add(e);
		int newSize = heap.size();
		return newSize > oldSize;
	}

	@Override
	public E remove() {
		return heap.remove();
	}

	@Override
	public E poll() {
		return heap.remove();
	}

	@Override
	public E element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() {
		return heap.get();
	}

}
