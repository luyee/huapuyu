package com.anders.experiment.多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	public static void main(String[] args) {
		// 可以理解为锁，同一时间，锁只允许一个线程占有锁，其他线程等待；信号量可允许指定参数（比如这里的3）个线程执行，其他线程等待
		Semaphore semaphore = new Semaphore(3);

		// ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
		// ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));
		// 控制线程池中只有5个活动线程
		ExecutorService tpe = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 10; i++) {
			tpe.execute(new ThreadClass(semaphore));
		}
	}
}

class ThreadClass extends Thread {
	private Semaphore semaphore;

	public ThreadClass(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : start");
		try {
			this.semaphore.acquire();
			System.out.println(this.getName() + " : acquire and sleep 5 seconds");
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		this.semaphore.release();
		System.out.println(this.getName() + " : release and end");
	}
}
