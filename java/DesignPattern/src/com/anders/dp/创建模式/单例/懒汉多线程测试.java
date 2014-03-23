package com.anders.dp.创建模式.单例;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class 懒汉多线程测试 {
	private static 懒汉多线程测试 singleton = null;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private 懒汉多线程测试() {
	}

	public static final 懒汉多线程测试 getInstance(String threadName) throws InterruptedException {
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tgetInstance\tstart");
		if (null == singleton) {
			System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tgetInstance\twait");
			synchronized (懒汉.class) {
				if (null == singleton) {
					singleton = new 懒汉多线程测试();
					System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tgetInstance\tsleep");
					Thread.sleep(10000);
				}
			}
		}
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tgetInstance\tend");
		return singleton;
	}

	public void print(String threadName) throws InterruptedException {
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tprint\t\trun");
		// Thread.sleep(10000);
	}

	public synchronized void synPrint(String threadName) throws InterruptedException {
		Thread.sleep(10000);
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tsynPrint\trun");
	}

	public void synBlock(String threadName) throws InterruptedException {
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tsynBlock\tstart");
		synchronized (懒汉.class) {
			Thread.sleep(10000);
			System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tsynBlock\trun");
		}
		System.out.println(dateFormat.format(new Date()) + "\t" + threadName + "\tsynBlock\tend");
	}

	public static void main(String[] args) {
		new SingletonThread().start();
		new SingletonThread().start();
		// new MyThread().start();
		// new MyThread().start();
		// new MyThread().start();
	}
}

class SingletonThread extends Thread {
	@Override
	public void run() {
		try {
			懒汉多线程测试 singleton = 懒汉多线程测试.getInstance(getName());
			singleton.print(getName());
			singleton.synBlock(getName());
			singleton.synPrint(getName());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
