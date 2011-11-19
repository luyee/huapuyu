package com.anders.ssh.mock.jmock;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {

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
	public void test() {
		Mockery context = new JUnit4Mockery();
		final List<String> list = context.mock(List.class);
		context.checking(new Expectations() {
			{
				oneOf(list).get(0);
				will(returnValue("heguangdong"));
			}
		});
		Assert.assertEquals("heguangdong", list.get(0));
	}
}
