package com.anders.ssh.dao.mongo;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoClientTest {

	@Test
	public void test() throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("192.168.2.89", 27018);
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

	@Test
	public void testMapReduce() throws UnknownHostException, MongoException {

		Mongo mongo = new Mongo("192.168.2.89", 27018);
		DB db = mongo.getDB("anders");

		DBCollection books = db.getCollection("books");

		BasicDBObject book = new BasicDBObject();
		book.put("name", "Understanding JAVA");
		book.put("pages", 100);
		books.insert(book);

		book = new BasicDBObject();
		book.put("name", "Understanding JSON");
		book.put("pages", 200);
		books.insert(book);

		book = new BasicDBObject();
		book.put("name", "Understanding XML");
		book.put("pages", 300);
		books.insert(book);

		book = new BasicDBObject();
		book.put("name", "Understanding Web Services");
		book.put("pages", 400);
		books.insert(book);

		book = new BasicDBObject();
		book.put("name", "Understanding Axis2");
		book.put("pages", 150);
		books.insert(book);

		String map = "function() { " + "var category; " + "if ( this.pages >= 250 ) " + "category = 'Big Books'; " + "else " + "category = 'Small Books'; " + "emit(category, {name: this.name});}";

		String reduce = "function(key, values) { " + "var sum = 0; " + "values.forEach(function(doc) { " + "sum += 1; " + "}); " + "return {books: sum};} ";

		MapReduceCommand cmd = new MapReduceCommand(books, map, reduce, null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput out = books.mapReduce(cmd);

		for (DBObject o : out.results()) {
			System.out.println(o.toString());
		}
	}
}
