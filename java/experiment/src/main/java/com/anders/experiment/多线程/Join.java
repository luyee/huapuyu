package com.anders.experiment.多线程;

import org.apache.log4j.Logger;

public class Join {

	private static final Logger logger = Logger.getLogger(Join.class);

	public static void main(String[] args) throws InterruptedException {

		Join线程1 t1 = new Join线程1();
		Join线程2 t2 = new Join线程2();
		t1.start();
		t2.start();

		// t2执行完了才执行t1，t1执行完了才是主线程日志
		t2.join();
		t1.join();

		logger.debug("主线程结束");
	}

}

class Join线程1 extends Thread {

	private static final Logger logger = Logger.getLogger(Join线程1.class);

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			logger.debug(i);
		}
	}
}

class Join线程2 extends Thread {

	private static final Logger logger = Logger.getLogger(Join线程2.class);

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			logger.debug(i);
		}
	}
}