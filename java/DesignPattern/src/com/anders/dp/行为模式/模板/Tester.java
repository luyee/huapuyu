package com.anders.dp.行为模式.模板;

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
		PlayPES p1 = new PlayChina();
		PlayPES p2 = new PlayJapan();
		p1.play();
		p2.play();

		// HttpServlet类中的service方法就是模板方法，子类只需要继承HttpServlet的doGet, doPost等方法
	}
}
