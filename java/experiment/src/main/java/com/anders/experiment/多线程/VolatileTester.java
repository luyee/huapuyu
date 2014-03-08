package com.anders.experiment.多线程;

import org.apache.log4j.Logger;

//TODO Anders Zhu : 实在不能重现volatile
/**
 * 实在不能重现volatile
 * 
 * @author Anders Zhu
 * 
 */
public class VolatileTester {
	private static final Logger logger = Logger.getLogger(VolatileTester.class);

	public static void main(String[] args) throws InterruptedException {
		// 接口 value = new 非volatile类();
		// for (int i = 0; i < 100000; i++) {
		// new 线程(value).start();
		// }
		// logger.debug(value.getValue());
		//
		// logger.debug("***************************************************");
		//
		// value = new volatile类();
		// for (int i = 0; i < 100000; i++) {
		// new 线程(value).start();
		// }
		// logger.debug(value.getValue());

		// volatile不能提供原子性，因此上面的操作没有意义

		非volatile线程 t1 = new 非volatile线程();
		// for (int i = 0; i < 100; i++) {
		t1.start();
		// }
		Thread.sleep(5000);
		t1.setDone();

		// logger.debug("***************************************************");

		// volatile线程 t2 = new volatile线程();
		// for (int i = 0; i < 100; i++) {
		// new Thread(t2).start();
		// }
		// Thread.sleep(5000);
		// t2.setDone();
	}
}

interface 接口 {
	void inc();

	int getValue();

	void isDone();

	void setDone();
}

class 非volatile类 implements 接口 {
	private static final Logger logger = Logger.getLogger(非volatile类.class);

	private int value = 0;
	private boolean done = false;

	@Override
	public void inc() {
		++value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void isDone() {
		if (done) {
			// logger.debug("true");
		}
		else {
			logger.debug("false");
		}
	}

	@Override
	public void setDone() {
		done = true;
	}
}

class volatile类 implements 接口 {
	private static final Logger logger = Logger.getLogger(volatile类.class);

	private volatile int value = 0;
	private volatile boolean done = false;

	@Override
	public void inc() {
		++value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void isDone() {
		if (done) {
			// logger.debug("true");
		}
		else {
			logger.debug("false");
		}
	}

	@Override
	public void setDone() {
		done = true;
	}
}

class 线程 extends Thread {
	private 接口 value;

	@Override
	public void run() {
		value.inc();
	}

	public 线程(接口 value) {
		this.value = value;
	}
}

class 非volatile线程 extends Thread {
	private static final Logger logger = Logger.getLogger(非volatile线程.class);

	private boolean done = false;

	@Override
	public void run() {
		while (!done) {
			;
		}
	}

	public void setDone() {
		done = true;
	}
}

class volatile线程 extends Thread {
	private static final Logger logger = Logger.getLogger(volatile线程.class);

	private volatile boolean done = false;

	@Override
	public void run() {
		while (!done) {
			;
		}
	}

	public void setDone() {
		done = true;
	}
}
