package com.anders.mongo;

import java.util.List;

import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;

public class ClientTest {
	@Test
	public void test1() {
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(50);
		// build.autoConnectRetry(true);
		build.threadsAllowedToBlockForConnectionMultiplier(50);
		build.maxWaitTime(1000 * 60 * 2);
		build.connectTimeout(1000 * 60 * 1);

		MongoClientOptions myOptions = build.build();
		MongoClient mongoClient = null;
		try {
			// mongoClient = new MongoClient("anders1", myOptions);
			mongoClient = new MongoClient("anders1", 27018);
			List<String> names = mongoClient.getDatabaseNames();
			for (String name : names) {
				System.out.println(name);
			}
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
}
