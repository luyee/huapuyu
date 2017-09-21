package com.anders.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 30, 30, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());

		new Thread(new MonitorThread(threadPoolExecutor)).start();

		for (int i = 0; i < 100; i++) {
			threadPoolExecutor.execute(new TestThread(String.valueOf(i)));
		}
	}
}

class TestThread implements Runnable {

	private String name;

	public TestThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " : start waiting");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + " : end waiting");
	}
}

class MonitorThread implements Runnable {

	private ThreadPoolExecutor threadPoolExecutor;

	public MonitorThread(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*****************************************************************************");
			System.out.println("getQueue : " + threadPoolExecutor.getQueue().size());
			System.out.println("getCorePoolSize : " + threadPoolExecutor.getCorePoolSize());
			System.out.println("getActiveCount : " + threadPoolExecutor.getActiveCount());
			System.out.println("getCompletedTaskCount : " + threadPoolExecutor.getCompletedTaskCount());
			System.out.println("getTaskCount : " + threadPoolExecutor.getTaskCount());
			System.out.println("getPoolSize : " + threadPoolExecutor.getPoolSize());
			System.out.println("getLargestPoolSize : " + threadPoolExecutor.getLargestPoolSize());
			System.out.println("getMaximumPoolSize : " + threadPoolExecutor.getMaximumPoolSize());
			System.out.println("*****************************************************************************");
		}
	}

}
