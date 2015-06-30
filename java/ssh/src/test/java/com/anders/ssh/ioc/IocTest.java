package com.anders.ssh.ioc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class IocTest {

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
		// ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-test.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc-test.xml");
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-ioc-test.xml"));

		Pojo pojo = (Pojo) ctx.getBean("pojo");
		System.out.println(pojo);

		pojo = (Pojo) bf.getBean("pojo");
		System.out.println(pojo);

		SetterInjection si = (SetterInjection) ctx.getBean("setterInjection");
		System.out.println(si);

		ConstructorInjection ci = (ConstructorInjection) ctx.getBean("constructorInjection");
		System.out.println(ci);

		FactoryInjection fi = (FactoryInjection) ctx.getBean("factoryInjection");
		System.out.println(fi);
	}

}
