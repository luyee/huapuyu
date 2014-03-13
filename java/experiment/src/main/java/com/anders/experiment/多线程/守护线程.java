package com.anders.experiment.多线程;

public class 守护线程 {

	public static void main(String[] args) {

		DaemonThread daemonThread = new DaemonThread();
		// setDaemon设置为true后，主线程结束后，DaemonThread也结束；设置为false，则死循环
		daemonThread.setDaemon(false);
		daemonThread.start();

		System.out.println("主线程结束");
	}

}

class DaemonThread extends Thread {

	@Override
	public void run() {
		int i = 0;
		while (true) {
			System.out.println(i++);
		}
	}

}