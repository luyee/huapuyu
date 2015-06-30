package com.anders.dp.创建模式.原型.register;

import junit.framework.Assert;

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
		Prototype p1 = new ConcretePrototype();
		System.out.println(p1.getClass().getName() + "\t" + p1.hashCode());

		Prototype p2 = (Prototype) p1.clone();
		System.out.println(p2.getClass().getName() + "\t" + p2.hashCode());

		Assert.assertTrue(!p1.equals(p2));
	}
}
