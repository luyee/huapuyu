package com.anders.dp.创建模式.工厂方法;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.timer.TimerFactoryBean;

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
	public void test() throws Exception {
		IFactory factory = new 奥迪工厂类();
		ICar car = factory.factory();
		car.启动();
		car.停止();

		factory = new 宝马工厂类();
		car = factory.factory();
		car.启动();
		car.停止();

		// Java中的工厂方法模式
		// Collection就相当于抽象工厂类
		// ArrayList和HashSet就相当于奥迪工厂类和宝马工厂类
		// Iterator就相当于ICar
		// collection.iterator()就相当于factory.factory()返回奥迪或宝马
		Collection<String> collection = new ArrayList<String>();
		collection.add("奥迪");
		collection.add("宝马");
		// 返回java.util.AbstractList$Itr
		// private class Itr implements Iterator<E>
		System.out.println(collection.iterator().getClass().getName());
		for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println(object);
		}

		collection = new HashSet<String>();
		collection.add("大众");
		collection.add("奔驰");
		// 返回java.util.HashMap$KeyIterator
		// private final class KeyIterator extends HashIterator<K>
		// private abstract class HashIterator<E> implements Iterator<E> {
		System.out.println(collection.iterator().getClass().getName());
		for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println(object);
		}

		FactoryBean factoryBean = new ProxyFactoryBean();
		// 返回AopProxy，AopProxy继承Object
		Object object = factoryBean.getObject();
		System.out.println(object.getClass().getName());

		factoryBean = new TimerFactoryBean();
		// 返回Timer，Timer继承Object
		object = factoryBean.getObject();
		System.out.println(object.getClass().getName());
	}
}
