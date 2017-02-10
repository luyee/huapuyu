package com.anders.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

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

	@Test
	public void test3() {
		// 分片信息
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo si = new JedisShardInfo("localhost", 6379);
		si.setPassword("foobared");
		shards.add(si);
		si = new JedisShardInfo("localhost", 6380);
		si.setPassword("foobared");
		shards.add(si);

		// 池对象
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), shards);

		// 开始使用
		ShardedJedis jedis = pool.getResource();
		jedis.set("a", "foo");
		pool.returnResource(jedis);

		ShardedJedis jedis2 = pool.getResource();
		jedis.set("z", "bar");
		pool.returnResource(jedis);
		pool.destroy();
	}
}
