package dao.ibatis;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 如何在应用程序中使用ibatis
 * 
 * @author Anders
 * 
 */
public class MainTest
{
	public static void main(String[] args) throws IOException
	{
		Reader reader = Resources.getResourceAsReader("sqlmap-config.xml");
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
}
