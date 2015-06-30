package com.anders.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class ClientTest {
	@Test
	public void test1() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(30);
		config.setMaxWaitMillis(3 * 1000);

		// JedisPool pool = new JedisPool(config, "anders2", 6379);
		Set<String> sentinels = new HashSet<String>();
		sentinels.add(new HostAndPort("anders4", 26379).toString());
		sentinels.add(new HostAndPort("anders5", 26379).toString());
		sentinels.add(new HostAndPort("anders6", 26379).toString());
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis client = pool.getResource();
		try {
			String result = client.set("key-string", "Hello, Redis!");
			System.out.println(String.format("set指令执行结果:%s", result));
			String value = client.get("key-string");
			System.out.println(String.format("get指令执行结果:%s", value));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
		}

		pool.close();
	}
}
