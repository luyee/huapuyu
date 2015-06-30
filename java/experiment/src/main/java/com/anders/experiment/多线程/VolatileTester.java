package com.anders.experiment.多线程;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不能重现volatile的原因：只有在对变量读取频率很高的情况下，虚拟机才不会及时回写主内存，而当频率没有达到虚拟机认为的高频率时，普通变量和volatile是同样的处理逻辑。 如在每个循环中执行System .out.println("")加大了读取变量的时间间隔，使虚拟机认为读取频率并不那么高，所以实现了和添加volatile关键字一样的效果。volatile的效果在jdk1.2及之前很容易重现 ，但随着虚拟机的不断优化，如今的普通变量的可见性已经不是那么严重的问题了，这也是volatile如今确实不太有使用场景的原因吧。
 * 
 * @author Anders Zhu
 * 
 */
public class VolatileTester extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(VolatileTester.class);

	private static boolean done = false;

	@Override
	public void run() {
		while (!done) {
		}
	}

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
		for (int i = 0; i < 100; i++) {
			new VolatileTester().start();
		}
		Thread.sleep(5000);
		done = true;

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
	private static final Logger LOG = LoggerFactory.getLogger(非volatile类.class);

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
		} else {
			LOG.debug("false");
		}
	}

	@Override
	public void setDone() {
		done = true;
	}
}

class volatile类 implements 接口 {
	private static final Logger LOG = LoggerFactory.getLogger(volatile类.class);

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
		} else {
			LOG.debug("false");
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