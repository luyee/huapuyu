package com.anders.experiment.多线程;

public class JavaVolatile {
	public static void main(String[] args) {
		final JavaVolatile volObj = new JavaVolatile();
		Thread t1 = new Thread() {
			public void run() {
				System.out.println("t1 start");
				for (;;) {
					volObj.waitToExit();
				}
			}
		};
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				System.out.println("t2 start");
				for (;;) {
					volObj.swap();
				}
			}
		};
		t2.start();
	}

	volatile boolean boolValue;

	public void waitToExit() {
		if (boolValue == !boolValue)
			System.exit(0);
	}

	public void swap() {
		boolValue = !boolValue;
	}

}
