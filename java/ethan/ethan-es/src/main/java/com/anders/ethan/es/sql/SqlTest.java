package com.anders.ethan.es.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

public class SqlTest {

	@Test
	public void test() throws Exception {
		Properties properties = new Properties();
        properties.put("url", "jdbc:elasticsearch://192.168.56.101:9300/ni-database-zhuzhen");
        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        Connection connection = dds.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * from ni-database-zhuzhen where empname='emp11'");
        ResultSet resultSet = ps.executeQuery();
        List<String> result = new ArrayList<String>();
        while (resultSet.next()) {
              System.out.println(resultSet.getString("empname"));
        }
        ps.close();
        connection.close();
        dds.close();
	}

}
