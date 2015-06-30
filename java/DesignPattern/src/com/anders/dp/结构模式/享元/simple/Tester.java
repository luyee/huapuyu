package com.anders.dp.结构模式.享元.simple;

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
		FlyweightFactory factory = new FlyweightFactory();
		// 这里的'a' 叫内蕴 或者 内部状态
		Flyweight fly = factory.factory(new Character('a'));
		// 这里的'first call' 叫外蕴 或者 外部状态 其最好选择java基本类型（如String）因为效率会更高一些（未验证）
		// 对于自己构造的外蕴对象，注意equals和hashcode的复写
		fly.operation("first call");
		fly = factory.factory(new Character('b'));
		fly.operation("second call");
		fly = factory.factory(new Character('c'));
		fly.operation("third call");
		fly = factory.factory(new Character('a'));
		fly.operation("fourth call");

		factory.checkFlyweight();
	}
}