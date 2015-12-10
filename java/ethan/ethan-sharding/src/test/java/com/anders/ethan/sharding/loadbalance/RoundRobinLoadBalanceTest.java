package com.anders.ethan.sharding.loadbalance;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RoundRobinLoadBalanceTest {
	@Test(expected = IllegalArgumentException.class)
	public void test1() {
		new RoundRobinLoadBalance(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2() {
		new RoundRobinLoadBalance(new HashMap<String, Integer>());
	}

	@Test
	public void test3() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("read01", 2);
		map.put("read02", 3);
		LoadBalance<String> lb = new RoundRobinLoadBalance(map);
		for (int i = 0; i < 20; i++) {
			System.out.println(lb.elect());
		}
	}
}
