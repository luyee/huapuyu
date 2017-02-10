package com.anders.dp.创建模式.抽象工厂;

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
		I俱乐部 club = new 尤文图斯();
		I前锋 forward = club.factory前锋();
		I球场 field = club.factory球场();
		forward.姓名();
		forward.年龄();
		field.名称();
		field.地点();

		club = new 皇家马德里();
		forward = club.factory前锋();
		field = club.factory球场();
		forward.姓名();
		forward.年龄();
		field.名称();
		field.地点();

		// AbstractBeanFactory是典型的抽象工厂模式
	}
}
