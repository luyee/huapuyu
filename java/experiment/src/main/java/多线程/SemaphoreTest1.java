package 多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest1 {
	public static void main(String[] args) {
		// 也可以通过A线程获取信号量，B线程释放信号量实现特别的功能
		Semaphore semaphore = new Semaphore(1);

		// ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
		// ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));
		// 控制线程池中只有5个活动线程
		ExecutorService tpe = Executors.newFixedThreadPool(5);

		tpe.execute(new ThreadClass1(semaphore));
		tpe.execute(new ThreadClass1(semaphore));
		tpe.execute(new ThreadClass2(semaphore));
	}
}

/**
 * 获取信号量
 * 
 * @author Anders Zhu
 * 
 */
class ThreadClass1 extends Thread {
	private Semaphore semaphore;

	public ThreadClass1(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : 获取信号量开始");
		try {
			this.semaphore.acquire();
			System.out.println(this.getName() + " : 成功获取信号量");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(this.getName() + " : 获取信号量结束");
	}
}

/**
 * 释放信号量
 * 
 * @author Anders Zhu
 * 
 */
class ThreadClass2 extends Thread {
	private Semaphore semaphore;

	public ThreadClass2(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : 释放信号量开始");
		try {
			System.out.println(this.getName() + " : 睡10秒钟");
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(this.getName() + " : 成功释放信号量并退出");
		this.semaphore.release();
	}
}
