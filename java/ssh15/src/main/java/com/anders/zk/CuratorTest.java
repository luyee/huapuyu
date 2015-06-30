package com.anders.zk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class CuratorTest {
	// 查看节点状态
	@Test
	public void test1() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		retryPolicy = new RetryNTimes(3, 1000);
		retryPolicy = new RetryOneTime(1000);
		retryPolicy = new RetryUntilElapsed(1000, 1000);

		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();

		System.out.println(client.getState());
	}

	// 创建节点，指定namespace
	@Test
	public void test2() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		client.create().creatingParentsIfNeeded().forPath("/test1", "helloworld".getBytes());

		Stat stat = new Stat();
		System.out.println(new String(client.getData().storingStatIn(stat).forPath("/test1")));
		System.out.println(stat.getVersion());
		client.delete().withVersion(stat.getVersion()).forPath("/test1");
	}

	// 创建节点，不指定namespace
	@Test
	public void test3() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").retryPolicy(retryPolicy)
				.connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		client.create().creatingParentsIfNeeded().forPath("/anders_test/test2", "sayhello".getBytes());

		Stat stat = new Stat();
		System.out.println(new String(client.getData().storingStatIn(stat).forPath("/anders_test/test2")));
		System.out.println(stat.getVersion());

		stat = client.setData().withVersion(stat.getVersion()).forPath("/anders_test/test2");
		System.out.println(stat.getVersion());
		stat = client.setData().withVersion(stat.getVersion()).forPath("/anders_test/test2");
		System.out.println(stat.getVersion());

		client.delete().withVersion(stat.getVersion()).forPath("/anders_test/test2");
	}

	// 创建临时节点，不指定namespace
	@Test
	public void test4() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").retryPolicy(retryPolicy)
				.connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/anders_test/test3/user/name",
				"zhuzhen".getBytes());
	}

	// 创建序列节点并删除，不指定namespace
	@Test
	public void test5() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").retryPolicy(retryPolicy)
				.connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();

		String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath("/anders_test/test3/user/name", "zhuzhen".getBytes());

		client.delete().forPath(path);
	}

	// 创建序列节点并删除
	@Test
	public void test6() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();

		String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath("/test3/user/name", "zhuzhen".getBytes());

		System.out.println(path);

		client.delete().forPath(path);
	}

	@Test
	public void test7() throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		ExecutorService tp = Executors.newFixedThreadPool(2);
		client.create().creatingParentsIfNeeded().inBackground(new BackgroundCallback() {
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println(event.getData());
				System.out.println(event.getResultCode());
				System.out.println(event.getPath());
				System.out.println(Thread.currentThread());
			}
		}, tp).forPath("/test4", "just do it".getBytes());
		client.create().creatingParentsIfNeeded().inBackground(new BackgroundCallback() {
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println(event.getData());
				System.out.println(event.getResultCode());
				System.out.println(event.getPath());
				System.out.println(Thread.currentThread());
			}
		}).forPath("/test4", "just do it2".getBytes());
		client.delete().forPath("/test4");
	}

}
