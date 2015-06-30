package com.anders.dp.结构模式.享元.composite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		FlyweightFactory factory = new FlyweightFactory();
		Flyweight fly = factory.factory("aba");
		fly.operation("composite call");
		factory.checkFlyweight();
	}
}