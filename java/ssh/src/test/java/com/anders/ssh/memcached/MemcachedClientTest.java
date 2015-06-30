package com.anders.ssh.memcached;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-memcached-test.xml" })
public class MemcachedClientTest {
	@Resource(name = "memcachedClient")
	private MemcachedClient memcachedClient;

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		List<String> list = new ArrayList<String>();
		list.add("Tom");
		list.add("Jim");
		memcachedClient.add("users", 3600, list);
		Assert.assertEquals(2, ((List<String>) memcachedClient.get("users")).size());
	}

	@Test
	public void testSet() {
		memcachedClient.set("name", 3600, "zhuzhen");
	}

	@Test
	public void testGet() {
		Assert.assertEquals("zhuzhen", (String) memcachedClient.get("name"));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNull() {
		memcachedClient.set("userList", 3600, null);
	}

	@Test
	public void testSetEmpty() {
		memcachedClient.set("userList", 3600, new ArrayList<String>(0));
	}

	@Test
	public void testAddSuccess() {
		memcachedClient.delete("justMe");
		OperationFuture<Boolean> lock = memcachedClient.add("justMe", 3600, "ok");
		try {
			Assert.assertTrue(lock.get());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddFail() {
		OperationFuture<Boolean> lock = memcachedClient.add("justMe", 3600, "ok");
		try {
			Assert.assertFalse(lock.get());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
