package com.anders.experiment.多线程.阻塞队列;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		MyBlockingQueue myBlockingQueue = new MyBlockingQueue(3);
		myBlockingQueue.put(1);
		myBlockingQueue.put(2);
		myBlockingQueue.put(3);

		myBlockingQueue.take();

		myBlockingQueue.put(4);

		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());
		System.out.println(myBlockingQueue.take());

	}
}

class MyBlockingQueue {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	final Object[] items;
	int putptr = 0;
	int takeptr = 0;
	int count = 0;

	public MyBlockingQueue(int size) {
		items = new Object[size];
	}

	public void put(Object x) throws InterruptedException {
		lock.lock();// 第一步实现互斥
		try {
			while (count == items.length) {
				// 如果没有往数组放,线程阻塞
				System.out.println(x + " : put blocked");
				notFull.await();
			}
			items[putptr] = x;
			if (++putptr == items.length) {
				putptr = 0;// 如果putptr已经是数组的最后一个,那么putptr置为0,从第一个开始放
			}
			++count;// 放完后,把总数加一
			notEmpty.signal();// 通知其他线程可以取了
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				System.out.println("take blocked");
				notEmpty.await();
			}
			Object x = items[takeptr];
			if (++takeptr == items.length) {
				takeptr = 0;
			}
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}
