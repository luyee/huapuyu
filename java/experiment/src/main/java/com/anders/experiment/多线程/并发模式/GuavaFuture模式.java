package com.anders.experiment.多线程.并发模式;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 简单点说：Future模式就是把子任务交给线程池去执行，然后主线程去做一些其他的事，做完后通过future.get()去获取子任务完成的结果，如果子任务还没有完成，则主线程继续等待
 * 
 * @author Anders
 * 
 */
public class GuavaFuture模式 {
	public static void main(String[] args) {
		// ------------------------------------invoke 1
		// ExecutorService exe1 = Executors.newCachedThreadPool();
		// ExecutorService exe1 = Executors.newFixedThreadPool(3);
		// ExecutorService exe1 = Executors.newSingleThreadExecutor();
		// ExecutorService exe1 = Executors.newSingleThreadScheduledExecutor();
		// ExecutorService exe1 = Executors.newScheduledThreadPool(3);

		// for (int i = 0; i < 5; i++)
		// {
		// exe1.execute(new LiftOff());
		// }
		// exe1.shutdown();

		// ------------------------------------invoke 2
		ExecutorService exe1 = Executors.newCachedThreadPool();
		ListeningExecutorService executorService = MoreExecutors.listeningDecorator(exe1);

		for (int i = 0; i < 10; i++) {
			ListenableFuture<String> future = null;
			if (i % 2 == 0) {
				future = executorService.submit(new TaskWithResult2(i));
			} else {
				future = executorService.submit(new TaskWithResult3(i));
			}

			// 第一种方式，添加监听器，会报错
			// future.addListener(new Listen(future), executorService);

			// 第二种方式，添加回调函数，没问题
			Futures.addCallback(future, new FutureCallback<String>() {

				@Override
				public void onSuccess(String result) {
					System.out.println(result);
				}

				@Override
				public void onFailure(Throwable t) {
					System.out.println(t);
				}
			});
		}
		System.out.println("线程池关闭");
		exe1.shutdown();
		// exe1.shutdownNow();

	}
}

class Listen implements Runnable {

	private ListenableFuture<String> future;

	public Listen(ListenableFuture<String> future) {
		this.future = future;
	}

	@Override
	public void run() {
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}

class TaskWithResult2 implements Callable<String> {
	private int id;

	public TaskWithResult2(int id) {
		this.id = id;
	}

	public String call() {
		System.out.println(Thread.currentThread() + " : 休眠5秒");

		try {
			Thread.sleep(5000);
			System.out.println(this + "休眠5秒");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "result of TaskWithResult " + id;
	}
}

class TaskWithResult3 implements Callable<String> {
	private int id;

	public TaskWithResult3(int id) {
		this.id = id;
	}

	public String call() {
		System.out.println(Thread.currentThread() + " : 休眠10秒");

		try {
			Thread.sleep(10000);
			System.out.println(this + "休眠10秒");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "result of TaskWithResult " + id;
	}
}
