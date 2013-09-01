package hash;

import java.util.HashMap;
import java.util.Map;

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

		Long value1 = new Long(3);
		Long value2 = new Long(4);

		System.out.println("********** equals");
		System.out.println(user1.equals(user2));
		System.out.println(value1.equals(value2));

		System.out.println("********** hashCode");
		System.out.println(user1.hashCode());
		System.out.println(user2.hashCode());
		System.out.println(value1.hashCode());
		System.out.println(value2.hashCode());

		System.out.println("********** hash");
		System.out.println(hash(user1.hashCode()));
		System.out.println(hash(user2.hashCode()));
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
		System.out.println(hash(value1.hashCode() & 15));
		System.out.println(hash(value2.hashCode()) & 15);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(user1, 1L);
		map.put(user2, 2L);
		map.put(value1, 3L);
		map.put(value2, 4L);

		System.out.println("********** get");
		System.out.println(map.get(user2));
		System.out.println(map.get(user1));
		System.out.println(map.get(value1));
		System.out.println(map.get(value2));
	}

	private int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
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
		// 放置第13个对象之前，Entry数组的size为16，阀值为12
		// 放置第13个对象之后，Entry数组的size为32，阀值为24
		map.put(13L, 13L);
	}

}
