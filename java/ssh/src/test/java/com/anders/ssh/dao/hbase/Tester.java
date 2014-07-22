package com.anders.ssh.dao.hbase;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-hbase-test.xml" }, inheritLocations = true)
public class Tester {

	@Autowired
	private HbaseTemplate hbaseTemplate;

	@Test
	public void test() {
		hbaseTemplate.execute("account", new TableCallback<Account>() {
			@Override
			public Account doInTable(HTableInterface table) throws Throwable {
				Put put = new Put(Bytes.toBytes("123"));
				put.add(Bytes.toBytes("option"), Bytes.toBytes("name"), Bytes.toBytes("zhuzhen"));
				table.put(put);
				return null;
			}
		});

	}

	@Test
	public void test1() {
		Scan scan = new Scan();

		PageFilter pageFilter = new PageFilter(1000);
		scan.setFilter(pageFilter);
		scan.setStartRow(Bytes.toBytes("123"));
		scan.setStopRow(Bytes.toBytes("123"));

		scan.setCaching(100);
		scan.setCacheBlocks(true);
		hbaseTemplate.find("account", scan, new RowMapper<Account>() {

			@Override
			public Account mapRow(Result result, int rowNum) throws Exception {
				String name = Bytes.toString(result.getValue(Bytes.toBytes("option"), Bytes.toBytes("name")));
				System.out.println(name);
				return null;
			}
		});

		hbaseTemplate.find("account", "option", new RowMapper<Account>() {

			@Override
			public Account mapRow(Result result, int rowNum) throws Exception {
				String name = Bytes.toString(result.getValue(Bytes.toBytes("option"), Bytes.toBytes("name")));
				System.out.println(name);
				return null;
			}
		});

	}
}
