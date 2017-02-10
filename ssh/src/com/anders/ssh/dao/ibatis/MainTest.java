package com.anders.ssh.dao.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.anders.ssh.bo.test.Account;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 如何在应用程序中使用ibatis
 * 
 * @author Anders
 * 
 */
public class MainTest {
	public static void main(String[] args) throws SQLException, IOException {
		Reader reader = Resources.getResourceAsReader("config/ibatis-config.xml");
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);

		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);

		sqlMapClient.insert("save", account);
		reader.close();
	}
}
