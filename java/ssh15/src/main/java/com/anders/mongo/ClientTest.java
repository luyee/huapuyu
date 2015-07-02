package com.anders.mongo;

import java.util.List;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

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
			mongoClient = new MongoClient(new ServerAddress("anders1", 27018), myOptions);
			// mongoClient = new MongoClient("anders1", 27018);
			List<String> names = mongoClient.getDatabaseNames();
			for (String name : names) {
				System.out.println(name);
			}

			MongoDatabase mongoDatabase = mongoClient.getDatabase("anders");
			MongoCollection<Document> collection = mongoDatabase.getCollection("depart");
			Document doc = new Document();
			doc.put("name", "zhuzhen");
			doc.put("age", 33);
			collection.insertOne(doc);

			BasicDBObject query = new BasicDBObject("name", "zhuzhen");
			FindIterable<Document> iterable = collection.find(query);
			MongoCursor<Document> cursor = iterable.iterator();

			while (cursor.hasNext()) {
				Document depart = cursor.next();
				System.out.println(depart.get("name"));
				System.out.println(depart.get("age"));
				System.out.println(depart.toString());
			}
			cursor.close();

		} catch (MongoException e) {
			e.printStackTrace();
		}

		// FIXME Anders 添加增删改方法

		// repl set
		// MongoClient mongoClient = new MongoClient(Arrays.asList(
		// new ServerAddress("localhost", 27017),
		// new ServerAddress("localhost", 27018),
		// new ServerAddress("localhost", 27019)));
	}
}
