package com.anders.experiment.hash;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 测试HashMap的HASH碰撞
 * 
 * @author Anders
 * 
 */
public class HashMapTester {

	@Test
	public void test() {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("zhuzhen");

		User user2 = new User();
		user2.setId(3L);
		user2.setName("zhuzhen");

		User user3 = new User();
		user3.setId(5L);
		user3.setName("zhuzhen");

		Long value1 = new Long(3);
		Long value2 = new Long(4);

		System.out.println("********** equals");
		System.out.println(user1.equals(user2));
		System.out.println(value1.equals(value2));

		System.out.println("********** hashCode");
		System.out.println(user1.hashCode());
		System.out.println(user2.hashCode());
		System.out.println(user3.hashCode());
		System.out.println(value1.hashCode());
		System.out.println(value2.hashCode());

		System.out.println("********** hash");
		System.out.println(hash(user1.hashCode()));
		System.out.println(hash(user2.hashCode()));
		System.out.println(hash(user3.hashCode()));
		System.out.println(hash(value1.hashCode()));
		System.out.println(hash(value2.hashCode()));

		/*
		 * 默认情况下HashMap自动生成一个长度为16的Entry数组，并设置阀值为12（0.75*16）
		 * put操作时，会根据对象的hashCode再进行hash，然后和15（16-1）进行与（&）操作从而得到对象放置在Entry数组中的位置
		 * 需要注意的是put操作后，当HashMap的size-1大于等于阀值时，Entry数组的size以及阀值会调整为之前的2倍
		 */
		System.out.println("********** index");
		System.out.println(hash(user1.hashCode()) & 15);
		System.out.println(hash(user2.hashCode()) & 15);
		System.out.println(hash(user3.hashCode()) & 15);
		System.out.println(hash(value1.hashCode() & 15));
		System.out.println(hash(value2.hashCode()) & 15);

		System.out.println("********** indexFor");
		System.out.println(indexFor(hash(user1.hashCode()), 16));
		System.out.println(indexFor(hash(user2.hashCode()), 16));
		System.out.println(indexFor(hash(user3.hashCode()), 16));
		System.out.println(indexFor(hash(value1.hashCode()), 16));
		System.out.println(indexFor(hash(value2.hashCode()), 16));

		Map<Object, Object> map = new HashMap<Object, Object>();
		// hash相同时，形成一个单链表，最后put的反而在最前面，user3->user2->user1
		// hash相同时，可以称为hash冲突，两种解决方案：链表法和开放地址法
		map.put(user1, 1L);
		map.put(user2, 2L);
		map.put(user3, 3L);
		map.put(value1, 3L);
		map.put(value2, 4L);

		System.out.println("********** get");
		System.out.println(map.get(user2));
		System.out.println(map.get(user3));
		System.out.println(map.get(user1));
		System.out.println(map.get(value1));
		System.out.println(map.get(value2));

		System.out.println("********** index");
		// 32768 : 1000 0000 0000 0000
		// 00015 : 0000 0000 0000 1111
		// 00000 : 0000 0000 0000 0000
		System.out.println(32768);
		System.out.println(32768 & 15);
		// 35080 : 1000 1001 0000 1000
		// 00015 : 0000 0000 0000 1111
		// 00008 : 0000 0000 0000 1000
		System.out.println(hash(32768));
		System.out.println(hash(32768) & 15);

	}

	private int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	private int indexFor(int h, int length) {
		return h & (length - 1);
	}

	@Test
	public void testResizeEntry() {
		/*
		 * 需要注意的是put操作后，当HashMap的size-1大于等于阀值时，Entry数组的size以及阀值会调整为之前的2倍
		 */
		Map<Long, Long> map = new HashMap<Long, Long>();
		map.put(1L, 1L);
		map.put(2L, 2L);
		map.put(3L, 3L);
		map.put(4L, 4L);
		map.put(5L, 5L);
		map.put(6L, 6L);
		map.put(7L, 7L);
		map.put(8L, 8L);
		map.put(9L, 9L);
		map.put(10L, 10L);
		map.put(11L, 11L);
		map.put(12L, 12L);
		Assert.assertEquals(12, map.size());
		// 放置第13个对象之前，Entry数组的size为16，阀值为12
		// 放置第13个对象之后，Entry数组的size为32，阀值为24
		map.put(13L, 13L);
	}

}
