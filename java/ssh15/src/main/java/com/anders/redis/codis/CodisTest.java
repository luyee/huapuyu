package com.anders.codis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;

public class CodisTest {
	public static void main(String[] args) {
		JedisResourcePool pool = new RoundRobinJedisPool("192.168.56.101",
				1000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
		Jedis jedis = pool.getResource();
		jedis.set("name", "zhuzhen");

		String name = jedis.get("name1");
		System.out.println(name);
	}
}
