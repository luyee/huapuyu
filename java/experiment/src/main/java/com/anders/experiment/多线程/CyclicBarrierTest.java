package com.anders.experiment.多线程;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier begin = new CyclicBarrier(3);

		ExecutorService tpe = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 2; i++) {
			tpe.execute(new 运动员1(begin));
		}

		Thread.sleep(5000);

		System.out.println("预备，发枪");

		Thread.sleep(5000);

		System.out.println("最后一个运动员开始跑");

		tpe.execute(new 运动员1(begin));

		tpe.shutdown();
	}
}

class 运动员1 extends Thread {
	private CyclicBarrier begin;

	public 运动员1(CyclicBarrier begin) {
		this.begin = begin;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : 开始");
		try {
			begin.await();
			System.out.println(this.getName() + " : 正在跑");
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(this.getName() + " : 跑完");
	}
}
