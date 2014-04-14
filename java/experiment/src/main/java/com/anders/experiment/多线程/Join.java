package com.anders.experiment.多线程;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Join {

	private static final Logger LOG = LoggerFactory.getLogger(Join.class);

	public static void main(String[] args) throws InterruptedException {

		Join线程1 t1 = new Join线程1();
		Join线程2 t2 = new Join线程2();
		t1.start();
		t2.start();

		// t2执行完了才执行t1，t1执行完了才是主线程日志
		t2.join();
		t1.join();

		LOG.debug("主线程结束");
	}

}

class Join线程1 extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Join线程1.class);

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			LOG.debug("{}", i);
		}
	}
}

class Join线程2 extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Join线程2.class);

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			LOG.debug("{}", i);
		}
	}
}