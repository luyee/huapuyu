package com.anders.experiment.多线程;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTester {

	public static void main(String[] args) {
		LockService lockService = new LockService();

		// while (true) {
		new LockThread(lockService).start();
		new LockThread(lockService).start();
		new LockThread(lockService).start();
		new LockThread(lockService).start();
		new LockThread(lockService).start();
		// }
	}

}

class LockService {
	private Lock lock = new ReentrantLock();

	public void lockTest() {
		if (!lock.tryLock()) {
			// lock.lock(); //没有抢到锁的线程挂起等待
			System.out.println("获取不到锁，退出");
			return;
		}

		try {
			System.out.println("获取锁后Sleep10秒钟");
			Thread.sleep(10000);
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			lock.unlock();
			System.out.println("释放锁");
		}
	}
}

class LockThread extends Thread {

	private LockService lockService;

	public LockThread(LockService lockService) {
		this.lockService = lockService;
	}

	@Override
	public void run() {
		if (lockService != null) {
			lockService.lockTest();
		}
	}
}
