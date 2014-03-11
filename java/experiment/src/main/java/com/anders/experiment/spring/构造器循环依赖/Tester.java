package com.anders.experiment.spring.构造器循环依赖;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

	@Test(expected = BeanCreationException.class)
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/anders/experiment/spring/构造器循环依赖/applicationContext.xml");
		System.out.println(ctx.getBean("classA"));
	}

}
