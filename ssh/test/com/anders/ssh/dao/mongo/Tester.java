package com.anders.ssh.dao.mongo;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Tester {

	@Test
	public void testDataAdd() throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("192.168.2.90", 27017);
		DB db = mongo.getDB("anders");
		DBCollection collection = db.getCollection("user");
		System.out.println(collection.getCount());
		// 增
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("name", "zhuzhen");
		dbObject.put("age", 29);
		collection.insert(dbObject);
		// 查
		BasicDBObject query = new BasicDBObject();
		query.put("name", "zhuzhen");
		DBCursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println(collection.findOne().get("name"));
		System.out.println(collection.findOne().get("age"));
		// 改
		DBCursor updateCursor = collection.find(query);
		if (updateCursor.hasNext()) {
			DBObject object = updateCursor.next();
			object.put("name", "guolili");
			collection.save(object);
		}
		// 删
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("name", "guolili");
		DBCursor deleteCursor = collection.find(deleteQuery);
		while (deleteCursor.hasNext()) {
			DBObject object = deleteCursor.next();
			collection.remove(object);
		}
		// collection.drop();
	}
}
