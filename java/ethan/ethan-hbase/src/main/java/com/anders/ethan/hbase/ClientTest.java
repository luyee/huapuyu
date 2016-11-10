package com.anders.ethan.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class ClientTest {

	@Test
	public void test1() throws IOException {
		// System.setProperty("hadoop.home.dir",
		// "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = HBaseConfiguration.create();
		// config.set("hbase.master", "192.168.56.101:16000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum",
				"192.168.56.101,192.168.56.102,192.168.56.103,192.168.56.104,192.168.56.105");
		// config.set("zookeeper.znode.parent","/hbase");

		Connection connection = ConnectionFactory.createConnection(config);
		Admin admin = connection.getAdmin();

		HTableDescriptor hTableDescriptors[] = admin.listTables();
		for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
			System.out.println(hTableDescriptor.getNameAsString());
		}

		Table table = connection
				.getTable(TableName.valueOf("test_distributed"));
		Put put = new Put(Bytes.toBytes("row4"));
		put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("a"),
				Bytes.toBytes("guolili"));
		table.put(put);

		// 批量插入
		/*
		 * List<Put> putList = new ArrayList<Put>(); puts.add(put);
		 * table.put(putList);
		 */

		table.close();

		if (null != admin)
			admin.close();
		if (null != connection)
			connection.close();
	}

	@Test
	public void test2() throws IOException {
		// System.setProperty("hadoop.home.dir",
		// "C:\\Users\\Anders\\git\\hadoop-common-2.2.0-bin");
		Configuration config = HBaseConfiguration.create();
		// config.set("hbase.master", "192.168.56.101:16000");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum",
				"192.168.56.101,192.168.56.102,192.168.56.103,192.168.56.104,192.168.56.105");
		// config.set("zookeeper.znode.parent","/hbase");

		Connection connection = ConnectionFactory.createConnection(config);

		Table table = connection
				.getTable(TableName.valueOf("tb_bank"));
		Put put = new Put(Bytes.toBytes("3"));
		put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"),
				Bytes.toBytes("guolili"));
		table.put(put);

		table.close();

		if (null != connection)
			connection.close();
	}

}
