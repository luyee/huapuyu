package com.anders.dp.创建模式.建造;

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
		new Director(new ConcreteBuilder1()).construct("朱振", "郭立立");
		new Director(new ConcreteBuilder2()).construct("郭立立", "朱振");

		// AbstractStringBuilder相当于Builder，其中toString()是抽象的，需要子类实现
		// StringBuilder继承AbstractStringBuilder并实现了toString()方法
		// StringBuffer继承AbstractStringBuilder并实现了toString()方法
		System.out.println(new StringBuilder().append("朱振").toString());
		System.out.println(new StringBuffer().append("朱振").toString());
	}
}
