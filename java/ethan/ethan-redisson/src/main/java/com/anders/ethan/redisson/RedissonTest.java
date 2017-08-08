package com.anders.ethan.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonTest {
	public static void main(String[] args) {
		Config config = new Config();
		// config.setUseLinuxNativeEpoll(true);
		config.useClusterServers().addNodeAddress("redis://192.168.56.101:6379")
				.addNodeAddress("redis://192.168.56.102:6379").addNodeAddress("redis://192.168.56.103:6379")
				.addNodeAddress("redis://192.168.56.104:6379").addNodeAddress("redis://192.168.56.105:6379")
				.addNodeAddress("redis://192.168.56.106:6379");

		RedissonClient redisson = Redisson.create(config);
		// RMap<String, String> map = redisson.getMap("anyMap");
		// map.put("zhuzhen", "guolili");
		// map.fastPut("guolili", "helloworld");

		// int i = 0;
		// while (true) {
		// try {
		// map.put(i++ + "", i++ + "");
		// // map.get((i - 2) + "");
		// System.out.println(map.get((i - 2) + ""));
		// } catch (Throwable e) {
		// System.out.println(e.getMessage());
		// }
		// }

		String keyname = "";
		for (int i = 0; i < 1000000; i++) {
			try {
				keyname = "keys" + i;
				RBucket<String> bucket = redisson.getBucket(keyname);
				bucket.set(keyname);
				// System.out.println(bucket.get());
			} catch (Throwable e) {
				System.out.println(keyname + " ******** " + e.getMessage());
			}
		}
	}
}
