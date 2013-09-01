package hash;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试HashSet的HASH碰撞
 * 
 * @author Anders
 * 
 */
public class HashSetTester {

	@Test
	public void test1() {
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
		System.out.println(user1 == user2);
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

		System.out.println("********** index");
		System.out.println(hash(user1.hashCode()) & 15);
		System.out.println(hash(user2.hashCode()) & 15);
		System.out.println(hash(value1.hashCode() & 15));
		System.out.println(hash(value2.hashCode()) & 15);

		/**
		 * 默认情况下HashSet自动生成一个HashMap，HashMap中的key为HashSet中的值，value为静态的Object对象
		 */
		Set<Object> set = new HashSet<Object>();
		set.add(user1);
		set.add(user2);
		set.add(value1);
		set.add(value2);

		System.out.println("********** contains");
		System.out.println(set.contains(user1));
		System.out.println(set.contains(user2));
		System.out.println(set.contains(value1));
		System.out.println(set.contains(value2));
	}

	private int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	@Test
	public void test2() {
		// HashMap放置数据时，除了需要比较对象的hash值，还需要比较对象的引用，下面的判断逻辑在HashMap的put和get中都使用到
		// if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
		// 所以执行完下面的操作后，HashSet的size为1
		Set<Long> set = new HashSet<Long>();
		for (int i = 1; i <= 12; i++)
			set.add(1L);
		set.add(1L);
		Assert.assertEquals(1, set.size());
	}

	@Test
	public void test3() {
		// HashMap放置数据时，除了需要比较对象的hash值，还需要比较对象的引用，下面的判断逻辑在HashMap的put和get中都使用到
		// if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
		// 所以执行完下面的操作后，HashSet的size为13
		Set<User> set = new HashSet<User>();
		for (int i = 1; i <= 12; i++)
			set.add(new User(new Long(i), String.valueOf(i)));
		set.add(new User(new Long(13), String.valueOf(13)));
		Assert.assertEquals(13, set.size());
	}
}
