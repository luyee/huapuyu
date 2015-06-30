package com.anders.ssh.dao.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.anders.ssh.bo.test.Account;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapClientTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException, SQLException {
		Reader reader = Resources.getResourceAsReader("config/ibatis-config.xml");
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);

		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);

		sqlMapClient.insert("save", account);
		sqlMapClient.delete("deleteById", 1L);
		reader.close();
	}

}
