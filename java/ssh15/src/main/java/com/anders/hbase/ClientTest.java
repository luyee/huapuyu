package com.anders.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.PoolMap.PoolType;
import org.junit.Test;

public class ClientTest {

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
	public void test4() throws IOException {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.master", "anders1:9000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", "anders1,anders2,anders3");

		HConnection conn = null;
		HTableInterface table = null;
		Result result = null;

		try {
			conn = HConnectionManager.createConnection(config);
			table = conn.getTable(Bytes.toBytes("order"));
			result = table.get(new Get(Bytes.toBytes("row1")));

			System.out.println(result);

			Put put = new Put(Bytes.toBytes("row3"));
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("age"), 556, Bytes.toBytes("1"));
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("name"), 556, Bytes.toBytes("zhuyichen"));
			table.put(put);

			Get get = new Get(Bytes.toBytes("row3"));
			get.setMaxVersions(2);
			result = table.get(get);
			// for (Cell cell : result.rawCells()) {
			// System.out.println("Rowkey : " + Bytes.toString(cell.getRow()) +
			// " Familiy:Quilifier : "
			// + Bytes.toString(CellUtil.cloneQualifier(cell)) + " Value : "
			// + Bytes.toString(CellUtil.cloneValue(cell)) + " Time : " +
			// cell.getTimestamp());
			// }
			List<KeyValue> kv = result.getColumn(Bytes.toBytes("cf"), Bytes.toBytes("age"));
			System.out.println(kv);
			System.out.println(kv.size());
			// FIXME Anders 如何查询版本
		} finally {
			if (table != null) {
				table.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}
}
