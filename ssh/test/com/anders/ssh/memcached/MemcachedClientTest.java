package com.anders.ssh.memcached;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.spy.memcached.MemcachedClient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class MemcachedClientTest {
	@Resource
	private MemcachedClient memcachedClient;

	@Test
	public void test1() {
		List<String> list = new ArrayList<String>();
		list.add("Tom");
		list.add("Jim");
		memcachedClient.add("users", 3600, list);
		Assert.assertEquals(2, ((List<String>) memcachedClient.get("users"))
				.size());
	}

	@Test
	public void testSet() {
		memcachedClient.set("name", 3600, "zhuzhen");
	}

	@Test
	public void testGet() {
		Assert.assertEquals("zhuzhen", (String) memcachedClient.get("name"));
	}
}
