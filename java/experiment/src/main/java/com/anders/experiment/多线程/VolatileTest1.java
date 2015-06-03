package com.anders.experiment.多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileTest1 {

	private volatile int i = 0;

	public static void main(String[] args) {
		new VolatileTest1().startThread();
	}

	public void startThread() {
		ExecutorService pool = Executors.newFixedThreadPool(5);

		for (int j = 0; j < 50; j++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("*************");
					System.out.println(i);
					i++;
					System.out.println(i);
					System.out.println("*************");
				}
			});
		}

		System.out.println(i);

	}
}