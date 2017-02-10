package com.anders.experiment.多线程.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

// 可以用于缓存key的效验，如果mark为true，表明已经被缓存
public class AtomicStampedReferenceTest {
	public static void main(String[] args) {
		String s = "zhuzhen";
		AtomicStampedReference<String> at = new AtomicStampedReference<String>(s, 1);
		System.out.println(at.getReference());
		System.out.println(at.getStamp());
		
		at.set("hello world", 2);
		System.out.println(at.getReference());
		System.out.println(at.getStamp());
		

		
		System.out.println(at.attemptStamp("hello world", 1));
		
		System.out.println(at.compareAndSet("hello world", "hello", 1, 3));
		System.out.println(at.compareAndSet("hello world", "hello", 2, 3));
	}
}
