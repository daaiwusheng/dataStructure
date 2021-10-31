package com.wy.heap;

import java.util.Comparator;

import com.mj.printer.BinaryTreeInfo;


@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements Heap<E> ,BinaryTreeInfo {
	
	private E[] elements;
	
	public BinaryHeap(E[] elementsOut,Comparator<E> comparator) {
		super(comparator);
		if (elementsOut != null && elementsOut.length > 0) {
			size = elementsOut.length;
			int capacity = Math.max(elementsOut.length, DEFAULT_CAPACITY);
			
			this.elements =(E[]) new Object[capacity];
			for (int i = 0; i < size; i++) {
				this.elements[i] = elementsOut[i];
			}
			
			heapify();
		}
	}
	
	public BinaryHeap(Comparator<E> comparator) {
		super(comparator);
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}
	
	public BinaryHeap() {
		this(null);
	}
	
	@Override
	public void clear() {
		size = 0;
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
	}

	@Override
	public void add(E element) {
		if (element == null) {
			return;
		}
		ensureCapacity(size + 1);
		elements[size] = element;
		size ++;
		siftUp(size-1);
	}

	@Override
	public E get() {
		if (elements.length>0) {
			return elements[0];
		}
		return null;
	}

	@Override
	public E remove() {
		if (isEmpty()) {
			return null;
		}
		E topE  = elements[0];
		
		if (size == 1) {
			size = 0;
			elements[0] = null;
			return topE;
		}
		
		int lastIndex = size - 1;
		
		elements[0] = elements[lastIndex];
		elements[lastIndex] = null;
		
		size --;

		siftDown(0);
		
		return topE;
	}

	private void heapify() {
		int length = elements.length;
		for (int i = (length>>1) - 1; i >= 0; i--) {
			siftDown(i);
		}
	}
	
	private void siftDown(int index) {
		if (index >= size - 1) {
			return;
		}
		int currentIndex = index;

		int limitOfNonLeaves = size >> 1;
		int limit = size;
		
		E topE = elements[currentIndex];
	
		while (currentIndex < limitOfNonLeaves) {
			
			int indexBigger = (currentIndex << 1) + 1;
			E elementBigger = null;
			if (indexBigger <limit) {
				elementBigger = elements[indexBigger];
			}
			
			int rightIndex = indexBigger + 1;
			E elementRight = null;
			if (rightIndex <limit) {
				elementRight = elements[rightIndex];
				if (elementRight != null && compare(elementRight, elementBigger)>0) {
					elementBigger = elementRight;
					indexBigger = rightIndex;
				}
			}	
			
			if (elementBigger != null && compare(elementBigger, topE) > 0) {
				elements[currentIndex] = elementBigger;
				currentIndex = indexBigger;
				elements[currentIndex] = topE;
			}
			else {
				return;
			}

		}

	}
	
	
	
	@Override
	public E replace(E element) {
		if (element == null) {
			return null;
		}
		
		if (isEmpty()) {
			size ++;
		}
		
		E topE = elements[0];
		elements[0] = element;
		
		if (size>1) {
			siftDown(0);
		}
		
		return topE;
	}

	public void travesal() {
		for (int i = 0; i < size; i++) {
			StringBuilder builder = new StringBuilder();
			builder.append(i);
			builder.append(": ");
			builder.append(elements[i]);
			System.out.println(builder.toString());
		}
	}
	
	private void ensureCapacity(int newSize) {
		int length = elements.length;
		if (newSize > length) {
			int newLength = length<<1;
			E[] newElements =(E[]) new Object[newLength];
			
			for (int i = 0; i < length; i++) {
				newElements[i] = elements[i];
			}
			elements = newElements;
		}
	}
	
	
	private void siftUp(int index) {
		
		if (index<=0) {
			return;
		}
		
		int currentIndex = index;
		
		E currentElement = elements[currentIndex];
		
		while (currentIndex>0) {
			int parentIndex = (int)((currentIndex - 1)/2);
			
			E parentElement = elements[parentIndex];
			
			if (compare(parentElement, currentElement) >= 0) {
				return;
			}
			else {
				
				elements[parentIndex] = currentElement;
				elements[currentIndex] = parentElement;
				
			}
			
			currentIndex = parentIndex;
			
		}
		
	}

	@Override
	public Object root() {
		if (isEmpty()) {
			return null;
		}
		return 0;
	}

	@Override
	public Object left(Object node) {
		
		int index = ((int)node << 1) + 1;
		
		return index<size ? index : null;
	}

	@Override
	public Object right(Object node) {
		int index = ((int)node << 1) + 2;

		return index < size ? index : null;
	}

	@Override
	public Object string(Object node) {
		
		return elements[(int)node];
	}

	
/*	//保留
	private void siftDown(int index) {
		if (index >= size - 1) {
			return;
		}
		int currentIndex = index;
		
		
		int limitOfNonLeaves = size >> 1;
		int limit = size - 1;
		
		E topE = elements[currentIndex];
		
		
		while (currentIndex < limitOfNonLeaves) {
			
			int leftIndex = (currentIndex << 1) + 1;
			E elementLeft = null;
			if (leftIndex <= limit) {
				elementLeft = elements[leftIndex];
			}
			
			int rightIndex = (currentIndex << 1) + 2;
			E elementRight = null;
			if (leftIndex <= limit) {
				elementRight = elements[rightIndex];
			}	
			
			if (elementLeft != null && elementRight != null) {
				//如果左侧和右侧节点值相等,那么走左边
				if (compare(elementLeft, elementRight)>=0 
						&& compare(elementLeft, topE) > 0) {
					elements[currentIndex] = elementLeft;
					currentIndex = leftIndex;
					
				}
				else if(compare(elementRight, elementLeft)>0 
						&& compare(elementRight, topE) > 0) {
					elements[currentIndex] = elementRight;
					currentIndex = rightIndex;
				}
				else {
					return;
				}
				
			}
			else if (elementLeft != null && elementRight == null 
					&& compare(elementLeft, topE) > 0) {
				
				elements[currentIndex] = elementLeft;
				currentIndex = leftIndex;

			}
			else {
				return;
			}
			
			elements[currentIndex] = topE;
		}

	}
*/
	
	
	
	
}
