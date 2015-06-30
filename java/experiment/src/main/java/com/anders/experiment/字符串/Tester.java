package com.anders.experiment.字符串;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
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
	public void testJDK6() {
		String str1 = new StringBuilder("计算机").append("软件").toString();
		assertEquals(str1.intern() == str1, false);

		String str2 = new StringBuilder("ja").append("va").toString();
		assertEquals(str2.intern() == str2, false);
	}

	public void testJDK7() {
		String str1 = new StringBuilder("计算机").append("软件").toString();
		assertEquals(str1.intern() == str1, true);

		// TODO Anders : 这里比较蹊跷，书上说因为“java”这个字符串已经出现过，和StringBuilder创建的“java”并不是同一个
		String str2 = new StringBuilder("ja").append("va").toString();
		assertEquals(str2.intern() == str2, false);

		String str3 = new StringBuilder("ja").append("va1").toString();
		assertEquals(str3.intern() == str3, true);
	}

}
