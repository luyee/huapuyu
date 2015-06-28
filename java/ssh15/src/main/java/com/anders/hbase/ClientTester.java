package com.anders.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Test;

public class ClientTester {

	@Test
	public void test1() {
		System.setProperty("hadoop.home.dir",
				"C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = new HBaseConfiguration().create();
		config.set("hbase.master", "anders1:9000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "anders1,anders2,anders3");
		try {
			HTable table = new HTable(config, "order".getBytes());
			HBaseAdmin admin = new HBaseAdmin(config);
			Get get = new Get("row1".getBytes());
			Result result = table.get(get);
			System.out.println(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
