package com.anders.experiment.多线程;

import java.util.concurrent.CountDownLatch;

public class VolatileTester {
	public static CountDownLatch latch = new CountDownLatch(100000);

	public static void main(String[] args) throws InterruptedException {
		ValueObject value = new ValueObject();

		for (int i = 0; i < 100000; i++) {
			new VolatileThread(value).start();
		}
		latch.await();
		System.out.println(value.getValue());
	}
}

class ValueObject {
	private int value = 0;

	public void inc() {
		++value;
	}

	public int getValue() {
		return value;
	}
}

class VolatileThread extends Thread {
	private ValueObject value;

	@Override
	public void run() {
		try {
			sleep(100);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		value.inc();
		VolatileTester.latch.countDown();
	}

	public VolatileThread(ValueObject value) {
		this.value = value;
	}
}
