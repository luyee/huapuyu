package com.anders.experiment.多线程;

public class JoinTest {
	public static void main(String[] args) {
		Thread thread = new TestThread();
		thread.start();

		/*
		 * try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		 */

		System.out.println("main thread");

		thread.interrupt();
	}
}

class TestThread extends Thread {
	public void run() {
		int i = 0;
		while (!isInterrupted()) {
			System.out.println(Thread.currentThread().getName() + i++);
		}

		/*
		 * for (int i = 0; i < 1000; i++) { System.out.println(this.currentThread().getName() + i);
		 * }
		 */
	}
}

class TestThread1 implements Runnable {
	public void run() {

		int i = 0;
		/*
		 * for (int i = 0; i < 1000; i++) { System.out.println(this.currentThread().getName() + i);
		 * }
		 */
	}
}
