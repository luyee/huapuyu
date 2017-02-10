package com.anders.ssh.mock.mockito;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

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
		LinkedList<String> mockedList = mock(LinkedList.class);
		// 此时调用get方法，是会返回null，因为还没有对方法调用的返回值做模拟
		System.out.println(mockedList.get(999));

		// 模拟方法调用的返回值
		// 模拟获取第一个元素时，返回字符串first
		when(mockedList.get(0)).thenReturn("first");
		// 此时打印输出first
		System.out.println(mockedList.get(0));

		// 模拟方法调用抛出异常
		// 模拟获取第二个元素时，抛出RuntimeException
		// when(mockedList.get(1)).thenThrow(new RuntimeException());
		// 此时将会抛出RuntimeException
		// System.out.println(mockedList.get(1));

		// 没有返回值类型的方法也可以模拟异常抛出：
		// doThrow(new RuntimeException()).when(mockedList).clear();
		mockedList.clear();

		// 模拟方法调用的参数匹配
		// anyInt()匹配任何int参数，这意味着参数为任意值，其返回值均是element
		when(mockedList.get(anyInt())).thenReturn("element");
		// 此时打印是element
		System.out.println(mockedList.get(999));

		// 验证方法调用次数
		// 调用add一次
		mockedList.add("once");
		// 下面两个写法验证效果一样，均验证add方法是否被调用了一次
		verify(mockedList).add("once");
		// verify(mockedList, times(1)).add("once1");
		// 还可以通过atLeast(int i)和atMost(int i)来替代time(int i)来验证被调用的次数最小值和最大值。
	}
}
