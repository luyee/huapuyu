package com.anders.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class CuratorLeaderSelectTest {
	@Test
	public void masterSelect1() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		LeaderSelector leaderSelector = new LeaderSelector(client, "/leader_select", new LeaderSelectorListener() {

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				System.out.println(newState.values());
			}

			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				System.out.println("master1 is selected");
			}
		});
		// leaderSelector.autoRequeue();
		leaderSelector.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void masterSelect2() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("anders1:2181,anders2:2181,anders3:2181").namespace("anders_test")
				.retryPolicy(retryPolicy).connectionTimeoutMs(5000).sessionTimeoutMs(5000).build();
		client.start();
		LeaderSelector leaderSelector = new LeaderSelector(client, "/leader_select", new LeaderSelectorListener() {

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				System.out.println(newState.values());
			}

			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				System.out.println("master2 is selected");
			}
		});
		// leaderSelector.autoRequeue();
		leaderSelector.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
