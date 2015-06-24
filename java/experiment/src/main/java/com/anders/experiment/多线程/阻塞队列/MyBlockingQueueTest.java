package com.anders.experiment.多线程.阻塞队列;

import org.junit.Test;

public class MyBlockingQueueTest {
	private static MyBlockingQueue myBlockingQueue = new MyBlockingQueue(3);
	
	@Test
	public void put1() throws InterruptedException {
		myBlockingQueue.put(1);
		myBlockingQueue.put(2);
		myBlockingQueue.put(3);
		myBlockingQueue.put(5);
		
		System.out.println("put ok");
	}
	
	@Test
	public void get1() throws InterruptedException {
		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());
		
		System.out.println("get ok");

	}
}
