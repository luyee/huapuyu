package com.anders.experiment.jvm;

import java.util.ArrayList;
import java.util.List;

public class ThreadVolatileTest {

	public volatile static List<String> names = new ArrayList<String>();
	public volatile static char[] array = new char[512 * 1024];

	public static void main(String[] args) throws InterruptedException {
		// for (int i = 0; i < 1000000; i++) {
		// names.add("测试线程会不会把堆中的对象拷贝到线程栈中");
		// }
		for (int i = 0; i < 512 * 1024; i++) {
			array[i] = '1';
		}

		new MyThread().start();
		new MyThread().start();
		new MyThread().start();

		for (int i = 0; i < 512 * 1024; i++) {
			System.out.print(array[0]);
		}
	}
}

class MyThread extends Thread {

	private static char[] array = new char[512 * 1024];

	@Override
	public void run() {
		// char[] array = new char[512 * 1024];

		// System.out.println(ThreadVolatileTest.array.length);
		for (int i = 1024; i < 512 * 1024; i++) {
			array[i] = '2';
		}
		// System.out.println(ThreadVolatileTest.array.length);

		try {
			sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.run();
	}

}