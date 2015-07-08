package com.anders.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
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
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, config);
		Jedis client = pool.getResource();
		try {
			String result = client.set("name", "guolili");
			System.out.println(result);
			String value = client.get("name");
			System.out.println(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (null != client) {
				pool.returnResource(client);
			}
		}

		pool.close();
	}

	@Test
	public void test2() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(30);
		config.setMaxWaitMillis(3 * 1000);

		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("anders7", 6379));
		// jedisClusterNodes.add(new HostAndPort("lu3", 6379));
		// jedisClusterNodes.add(new HostAndPort("lu2", 6379));
		// jedisClusterNodes.add(new HostAndPort("lu3", 6379));
		JedisCluster jc = new JedisCluster(jedisClusterNodes, config);

		try {
			String result = jc.set("name", "zhuzhen");
			System.out.println(result);
			String value = jc.get("name");
			System.out.println(value);

			result = jc.set("age", "33");
			System.out.println(result);
			value = jc.get("age");
			System.out.println(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		}
	}
}
