package com.anders.dp.结构模式.桥梁;

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
	public void test() {
		Abstraction abstraction = new 短信Abstraction();
		abstraction.setImplementor(new 短信());
		abstraction.sendMsg();

		abstraction = new 邮件Abstraction();
		abstraction.setImplementor(new 邮件());
		abstraction.sendMsg();
	}
}