package com.anders.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.junit.Test;

public class ClientTester {

	@Test
	public void test1() {
		Configuration config = new HBaseConfiguration().create();
		config.set("hbase.master", "anders1:9000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "anders1,anders2,anders3");
		try {
			HTable table = new HTable(config, "order".getBytes());
			HBaseAdmin admin = new HBaseAdmin(config);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
