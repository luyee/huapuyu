package com.anders.dp.行为模式.备忘录;

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
		// 正常的游戏流程
		Originator originator = new Originator();
		// 游戏记录管理器
		Caretaker caretaker = new Caretaker();

		originator.setState("on");
		caretaker.saveMemento(originator.createMemento());
		System.out.println(originator.getState());
		originator.setState("off");
		originator.restoreMemento(caretaker.retrieveMemento());
		System.out.println(originator.getState());
	}
}