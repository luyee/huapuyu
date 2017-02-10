package com.anders.experiment.多线程;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(3);

		ExecutorService tpe = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 4; i++) {
			tpe.execute(new 运动员(begin, end));
		}

		Thread.sleep(5000);

		System.out.println("预备，发枪");
		begin.countDown();

		end.await();
		System.out.println("跑步比赛结束");
	}
}

class 运动员 extends Thread {
	private CountDownLatch begin;
	private CountDownLatch end;

	public 运动员(CountDownLatch begin, CountDownLatch end) {
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : 开始");
		try {
			begin.await();
			System.out.println(this.getName() + " : 正在跑");
			Thread.sleep(5000);
		}
		catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		end.countDown();
		System.out.println(this.getName() + " : 跑完");
	}
}
