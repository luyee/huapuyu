package com.anders.experiment.多线程.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

// 可以用于缓存key的效验，如果mark为true，表明已经被缓存
public class AtomicMarkableReferenceTest {
	public static void main(String[] args) {
		String s = "zhuzhen";
		AtomicMarkableReference<String> at = new AtomicMarkableReference<String>(s, true);
		System.out.println(at.getReference());
		System.out.println(at.isMarked());
		
		at.set("hello world", false);
		System.out.println(at.getReference());
		System.out.println(at.isMarked());
		
		boolean[] bo = {true};
		System.out.println(at.get(bo));
		System.out.println(bo[0]);
		
		at.attemptMark("hello world", true);
		System.out.println(at.getReference());
		System.out.println(at.isMarked());
		
		System.out.println(at.compareAndSet("hello world", "hello", false, false));
		System.out.println(at.compareAndSet("hello world", "hello", true, false));
		
		System.out.println(at.attemptMark("已经被缓存了吗", true));
	}
}
