package com.anders.experiment.集合;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

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
	public void testHashSet和ArrayList区别() {
		List<String> list = new ArrayList<String>();
		list.add("朱振");
		list.add("郭立立");
		list.add("郭立立");
		Assert.assertEquals(3, list.size());

		Set<String> set = new HashSet<String>();
		set.add("朱振");
		set.add("郭立立");
		set.add("郭立立");
		Assert.assertEquals(2, set.size());
	}

	@Test
	public void test安全自删除集合元素() {
		List<String> list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");

		Assert.assertEquals(4, list.size());

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if ("李四".equals(it.next()))
				it.remove();
		}

		Assert.assertEquals(3, list.size());
	}

	// 下面方法抛出如下异常
	// java.util.ConcurrentModificationException
	// at
	// java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
	// at java.util.AbstractList$Itr.next(AbstractList.java:343)
	@Test(expected = ConcurrentModificationException.class)
	public void test非安全自删除集合元素1() {
		List<String> list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if ("李四".equals(it.next()))
				list.remove("李四");
		}
	}

	// 下面方法抛出如下异常
	// java.util.ConcurrentModificationException
	// at
	// java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
	// at java.util.AbstractList$Itr.next(AbstractList.java:343)
	@Test(expected = ConcurrentModificationException.class)
	public void test非安全自删除集合元素2() {
		List<String> list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");

		for (String name : list) {
			if ("李四".equals(name)) {
				list.remove("李四");
			}
		}
	}

	// 下面方法抛出如下异常
	// java.util.ConcurrentModificationException
	// at
	// java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
	// at java.util.AbstractList$Itr.next(AbstractList.java:343)
	@Test(expected = ConcurrentModificationException.class)
	public void test非安全自添加集合元素1() {
		List<String> list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if ("李四".equals(it.next()))
				list.add("赵六");
		}
	}

	// 下面方法抛出如下异常
	// java.util.ConcurrentModificationException
	// at
	// java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
	// at java.util.AbstractList$Itr.next(AbstractList.java:343)
	@Test(expected = ConcurrentModificationException.class)
	public void test非安全自添加集合元素2() {
		List<String> list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");

		for (String name : list) {
			if ("李四".equals(name)) {
				list.add("赵六");
			}
		}
	}

	@Test
	public void test对象引用() {
		MyVO myVO = new MyVO();
		List<String> list = myVO.getList();
		list.clear();

		for (Iterator<String> it = myVO.getList().iterator(); it.hasNext();) {
			Assert.fail();
		}
	}

	@Test
	public void test数组拷贝() {
		// 浅拷贝
		int[] nums1 = new int[] { 12, 13, 14 };
		int[] nums2 = nums1;
		Assert.assertTrue(nums1 == nums2);

		// 深拷贝（1.6版本之前用此方法）
		int[] nums3 = new int[3];
		System.arraycopy(nums1, 0, nums3, 0, 3);
		Assert.assertFalse(nums1 == nums3);

		// 深拷贝（1.6版本之后用此方法）
		int[] nums4 = Arrays.copyOf(nums1, nums1.length);
		Assert.assertFalse(nums1 == nums4);
	}
}
