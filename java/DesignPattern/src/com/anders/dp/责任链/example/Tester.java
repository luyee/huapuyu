package com.anders.dp.责任链.example;

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
	public void test数组Filter() {
		数组Filter mp = new 数组Filter();
		mp.setMsg("hello world, <script>, 敏感");
		String msg = mp.process();
		System.out.println(msg);
	}

	@Test
	public void test列表Filter1() {
		列表Filter fc = new 列表Filter();
		fc.addFilter(new HtmlFilter()).addFilter(new SesitiveFilter());

		列表FilterProcessor mp = new 列表FilterProcessor();
		mp.setMsg("hello world, <script>, 敏感");
		mp.setFc(fc);
		String msg = mp.process();
		System.out.println(msg);
	}

	@Test
	public void test列表Filter2() {
		列表Filter fc1 = new 列表Filter();
		fc1.addFilter(new HtmlFilter());
		列表Filter fc2 = new 列表Filter();
		fc2.addFilter(new SesitiveFilter());
		fc2.addFilter(fc1);

		列表FilterProcessor mp = new 列表FilterProcessor();
		mp.setMsg("hello world, <script>, 敏感");
		mp.setFc(fc2);
		String msg = mp.process();
		System.out.println(msg);
	}
}