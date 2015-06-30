package com.anders.experiment.多线程;

import java.util.concurrent.TimeUnit;

public class LiftOff implements Runnable {
	protected int countDown = 5;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {
	}

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + "), ";
	}

	public void run() {
		try {
			while (countDown-- > 0) {
				System.out.print(status());
				// Thread.yield();
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
