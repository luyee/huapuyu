package com.anders.experiment.多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			ExecutorService pool = Executors.newFixedThreadPool(5);
			// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
			Thread t1 = new MyThread();
			Thread t2 = new MyThread();
			Thread t3 = new MyThread();
			Thread t4 = new MyThread();
			Thread t5 = new MyThread();
			// 将线程放入池中进行执行
			pool.execute(t1);
			pool.execute(t2);
			pool.execute(t3);
			pool.execute(t4);
			pool.execute(t5);
			// 关闭线程池
			pool.shutdown();
		}
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
		try {
			sleep(5000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println(Thread.currentThread().getName() + "执行完毕。。。");
		}
	}
}
