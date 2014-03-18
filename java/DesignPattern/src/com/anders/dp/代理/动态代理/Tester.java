package com.anders.dp.代理.动态代理;

import java.lang.reflect.Proxy;

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

	// 动态代理其实就是java.lang.reflect.Proxy类动态的根据您指定的所有接口生成一个class byte，
	// 该class会继承Proxy类，并实现所有你指定的接口（您在参数中传入的接口数组）；
	// 然后再利用您指定的classloader将class byte加载进系统，
	// 最后生成这样一个类的对象，并初始化该对象的一些值，如invocationHandler，
	// 以即所有的接口对应的Method成员。 初始化之后将对象返回给调用的客户端。这样客户端拿到的就是一个实现你所有的接口的Proxy对象。
	@Test
	public void test()
	{
		Subject subject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), new ProxySubject());
		subject.request();
	}
}