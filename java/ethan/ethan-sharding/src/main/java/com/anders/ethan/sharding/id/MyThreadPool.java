package com.anders.ethan.sharding.id;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {

	public static IdGenerator idGenerator;

	public static void main(String[] args) {
		idGenerator = new IdGenerator();
		idGenerator.setMachineId("1234");

		ExecutorService pool = Executors.newFixedThreadPool(500);

		for (int i = 0; i < 500; i++) {
			pool.execute(new MyThread());
		}
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println(MyThreadPool.idGenerator.genId());
	}
}
