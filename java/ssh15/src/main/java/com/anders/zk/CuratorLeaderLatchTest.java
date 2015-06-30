package com.anders.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

public class CuratorLeaderLatchTest {
	@Test
	public void test1() throws Exception {
		List<CuratorFramework> clients = Lists.newArrayList();
		List<LeaderLatch> latches = Lists.newArrayList();
		TestingServer server = new TestingServer();
		try {
			for (int i = 0; i < 10; ++i) {
				CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(),
						new ExponentialBackoffRetry(1000, 3));
				clients.add(client);
				LeaderLatch latch = new LeaderLatch(client, "/anders/leader", "Client #" + i);
				latches.add(latch);
				client.start();
				latch.start();
			}

			Thread.sleep(20000);

			LeaderLatch currentLeader = null;
			for (int i = 0; i < 10; ++i) {
				LeaderLatch latch = latches.get(i);
				if (latch.hasLeadership()) {
					currentLeader = latch;
					System.err.println(i + " is leader");
				} else {
					System.err.println(i + " is not leader");
				}
			}

			System.out.println("current leader is " + currentLeader.getId());
			System.out.println("release the leader " + currentLeader.getId());
			currentLeader.close();
			latches.get(0).await(2, TimeUnit.SECONDS);
			System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
			System.out.println("the new leader is " + latches.get(0).getLeader().getId());

			System.out.println("Press enter/return to quit\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} finally {
			System.out.println("shutting down...");

			for (LeaderLatch latch : latches) {
				CloseableUtils.closeQuietly(latch);
			}
			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}
			CloseableUtils.closeQuietly(server);
		}
	}
}
