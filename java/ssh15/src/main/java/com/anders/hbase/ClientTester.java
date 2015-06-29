package com.anders.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.PoolMap.PoolType;
import org.junit.Test;

public class ClientTester {

	@Test
	public void test1() {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
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

	@Test
	public void test2() {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = HBaseConfiguration.create();
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

	@Test
	public void test3() {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = new HBaseConfiguration().create();
		config.set("hbase.master", "anders1:9000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "anders1,anders2,anders3");

		HTableFactory factory = new HTableFactory();
		HTablePool pool = new HTablePool(config, 30, factory, PoolType.ThreadLocal);

		HTableInterface table = null;
		Result result = null;

		try {
			table = (HTableInterface) pool.getTable(Bytes.toBytes("order1"));
			if (table == null) {
				throw new RuntimeException();
			}
			result = table.get(new Get(Bytes.toBytes("row1")));
			System.out.println(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void test4() {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.master", "anders1:9000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "anders1,anders2,anders3");

		HConnection hConnection = null;
		HTableInterface table = null;
		Result result = null;

		try {
			hConnection = HConnectionManager.createConnection(config);
			table = hConnection.getTable(Bytes.toBytes("order"));
			result = table.get(new Get(Bytes.toBytes("row1")));
			System.out.println(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (hConnection != null) {
				try {
					hConnection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
