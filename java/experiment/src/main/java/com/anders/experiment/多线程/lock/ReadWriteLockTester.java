package com.anders.experiment.多线程.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

/**
 * 当写操作时，其他线程无法读或写；当读操作时，其它线程无法写，但却可以读。
 * 
 * @author Anders Zhu
 * 
 */
public class ReadWriteLockTester {
	public static void main(String[] args) {
		测试类 t = new 测试类();

		// 测试先写后读
		// new Set线程(t).start();
		// new Get线程(t).start();
		// new Get线程(t).start();
		// 测试先读后写
		new Get线程(t).start();
		new Set线程(t).start();
		new Set线程(t).start();
	}
}

class Set线程 extends Thread {
	private 测试类 t;

	public Set线程(测试类 t) {
		this.t = t;
	}

	@Override
	public void run() {
		t.set();
		// t.get();

		super.run();
	}
}

class Get线程 extends Thread {
	private 测试类 t;

	public Get线程(测试类 t) {
		this.t = t;
	}

	@Override
	public void run() {
		t.get();
		t.set();
		super.run();
	}
}

class 测试类 {
	private static final Logger logger = Logger.getLogger(测试类.class);

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public void get() {
		readWriteLock.readLock().lock();
		try {
			logger.debug("read");
			// System.out.println(readWriteLock.getReadHoldCount());
			// System.out.println(readWriteLock.getReadLockCount());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public void set() {
		readWriteLock.writeLock().lock();
		try {
			logger.debug("write");
			// System.out.println(readWriteLock.getWriteHoldCount());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
