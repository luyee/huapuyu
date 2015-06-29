package com.anders.experiment.集合;

import java.util.Stack;

public class 各种集合 {
	public static void main(String[] args) {
		System.out.println(Math.abs("test".hashCode() % 2));

		// 线程安全
		// Collections.synchronizedMap(m);
		// Collections.synchronizedSet(s);
		// Collections.synchronizedSortedMap(m);
		// Collections.synchronizedSortedSet(s)
		// 只读
		// Collections.unmodifiableList(list)
		// Collections.unmodifiableMap(m)
		// Collections.unmodifiableSet(s)
		// Collections.unmodifiableSortedMap(m)
		// Collections.unmodifiableSortedSet(s)

		Stack<Long> stack = new Stack<Long>();
		stack.push(1L);
		stack.push(2L);
		stack.push(3L);

		System.out.println("size:" + stack.size());
		System.out.println("lastElement:" + stack.lastElement());
		System.out.println("size:" + stack.size());
		System.out.println("lastElement:" + stack.lastElement());
		System.out.println("firstElement:" + stack.firstElement());

		for (int i = 0; i < 3; i++) {
			System.out.println("pop " + i + ":" + stack.pop());
		}

		System.out.println("lastElement:" + stack.lastElement());
		System.out.println("firstElement:" + stack.firstElement());

	}
}
