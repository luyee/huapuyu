package 多线程.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

public class ReadWriteLockTester {
	public static void main(String[] args) {
		测试类 t = new 测试类();

		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
		new 线程(t).start();
	}
}

class 线程 extends Thread {
	private 测试类 t;

	public 线程(测试类 t) {
		this.t = t;
	}

	@Override
	public void run() {
		t.set();
		t.get();
		super.run();
	}
}

class 测试类 {
	private static final Logger logger = Logger.getLogger(测试类.class);

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public void get() {
		readWriteLock.readLock().lock();
		logger.debug("into read");
		try {
			logger.debug("read");
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public void set() {
		readWriteLock.writeLock().lock();
		logger.debug("into write");
		try {
			logger.debug("write");
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
