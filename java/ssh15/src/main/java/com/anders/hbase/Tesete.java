package com.anders.hbase;

import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.data.hadoop.pig.PigTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Tesete {

	public static void main(String[] args) {
		HbaseTemplate hbaseTemplate = new HbaseTemplate();
		HiveTemplate hiveTemplate = new HiveTemplate();
		PigTemplate pigTemplate = new PigTemplate();
		MongoTemplate mongoTemplate = new MongoTemplate(null);
	}

}
