package com.anders.zk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.junit.Assert;
import org.junit.Test;

public class CuratorLockTest2 {
	@Test
	public void test1() throws Exception {
		TestingServer server = new TestingServer();
		ExecutorService es = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; ++i) {
			CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(),
					new ExponentialBackoffRetry(1000, 3));
			client.start();

			es.submit(new MyThread(new DMutex(client), i));
		}

		Assert.assertTrue(true);
	}
}

class DMutex {

	private InterProcessMutex mutex;

	public DMutex(CuratorFramework client) {
		mutex = new InterProcessMutex(client, "/anders/mutex");
		System.out.println(mutex);
	}

	public void acquire() throws Exception {
		mutex.acquire();
	}

	public void release() throws Exception {
		mutex.release();
	}
}

class MyThread implements Runnable {

	private DMutex dMutex;
	private int i;

	public MyThread(DMutex dMutex, int i) {
		this.dMutex = dMutex;
		this.i = i;
	}

	@Override
	public void run() {
		try {
			dMutex.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(i + " sleep 3");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(i + " awake");

		try {
			dMutex.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}