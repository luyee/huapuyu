package com.anders.ethan.sharding.loadbalance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.anders.ethan.sharding.loadbalance.LoadBalance;
import com.anders.ethan.sharding.loadbalance.RandomLoadBalance;

public class RandomLoadBalanceTest {
	@Test(expected = IllegalArgumentException.class)
	public void test1() {
		new RandomLoadBalance(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2() {
		new RandomLoadBalance(new ArrayList<String>());
	}

	@Test
	public void test3() {
		List<String> list = new ArrayList<String>(2);
		list.add("read01");
		list.add("read02");
		LoadBalance<String> lb = new RandomLoadBalance(list);
		for (int i = 0; i < 20; i++) {
			System.out.println(lb.elect());
		}
	}
}
