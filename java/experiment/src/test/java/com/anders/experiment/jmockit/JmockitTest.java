package com.anders.experiment.jmockit;

import static mockit.Deencapsulation.invoke;

import java.sql.Connection;
import java.sql.SQLException;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class JmockitTest {

	@Mocked
	private DynamicDataSource dynamicDataSource;

	@Injectable
	private DynamicDataSourceKey dynamicDataSourceKey;

	@Injectable
	private Connection connection;

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
	public void test1() {
		new NonStrictExpectations() {
			{
				dynamicDataSource.determineCurrentLookupKey();
				result = "read01";
			}
		};

		Assert.assertEquals("read01", dynamicDataSource.determineCurrentLookupKey());

		new Verifications() {
			{
				dynamicDataSource.determineCurrentLookupKey();
				times = 1;
			}
		};
	}

	@Test
	public void test2() throws SQLException {
		final DynamicDataSource dynamicDataSource = new DynamicDataSource();

		new NonStrictExpectations(dynamicDataSource) {
			{
				invoke(dynamicDataSource, "getConnectionFromDataSource", anyString, anyString);
				result = connection;
			}
		};

		Assert.assertEquals(connection, dynamicDataSource.getConnection("root", "123"));
		Assert.assertEquals(connection, dynamicDataSource.getConnection());

		new Verifications() {
			{
				invoke(dynamicDataSource, "getConnectionFromDataSource", anyString, anyString);
				times = 2;
			}
		};
	}
}
