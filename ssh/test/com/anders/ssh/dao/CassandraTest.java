package com.anders.ssh.dao;

import java.sql.SQLException;
import java.util.List;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

import org.junit.Test;

public class CassandraTest
{

	@Test
	public void test1() throws ClassNotFoundException, SQLException
	{
		// Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
		// Connection con = DriverManager.getConnection("jdbc:cassandra://192.168.2.100:9160/ssh");

		// String query = "INSERT INTO user (KEY, name, age) VALUES (?, ?, ?)";
		// String query = "INSERT INTO data (KEY, name) VALUES (1, zhuzhen)";
		// PreparedStatement statement = con.prepareStatement(query);

		// statement.setString(1, "key1");
		// statement.setString(2, "zhuzhen");
		// statement.setLong(3, 1000);

		// statement.execute();

		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "192.168.2.100:9160");
		// ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("ssh", "ColumnFamilyName", ComparatorType.BYTESTYPE);
		// KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition("ssh", ThriftKsDef.DEF_STRATEGY_CLASS, replicationFactor, Arrays.asList(cfDef));
		System.out.println(cluster.describeClusterName());
		// 库：ssh
		KeyspaceDefinition kd = cluster.describeKeyspace("ssh");
		List<ColumnFamilyDefinition> cfdList = kd.getCfDefs();
		for (ColumnFamilyDefinition cfd : cfdList)
		{
			// 表
			System.out.println(cfd.getName());
			// 列
			List<ColumnDefinition> cdList = cfd.getColumnMetadata();
			System.out.println(cdList.size());
			for (ColumnDefinition cd : cdList)
			{
				System.out.println(cd.getName().getInt());
			}
		}

		Keyspace ksp = HFactory.createKeyspace("userKeyspace", cluster);
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(ksp, "data", StringSerializer.get(), StringSerializer.get());

		ColumnFamilyUpdater<String, String> updater = template.createUpdater("a key");
		updater.setString("domain", "www.datastax.com");
		updater.setLong("time", System.currentTimeMillis());

		try
		{
			template.update(updater);
		}
		catch (HectorException e)
		{
		}

		try
		{
			ColumnFamilyResult<String, String> res = template.queryColumns("a key");
			String value = res.getString("domain");
			System.out.println(value);
		}
		catch (HectorException e)
		{
		}
	}
}
