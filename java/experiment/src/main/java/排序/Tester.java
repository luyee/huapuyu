package 排序;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		List<User> userList = new ArrayList<User>();

		User user = new User();
		user.setName("zhuzhen");
		user.setAge(29);

		userList.add(user);

		user = new User();
		user.setName("guolili");
		user.setAge(26);

		userList.add(user);

		System.out.println("未排序List");
		for (User u : userList) {
			System.out.println(u.getName() + " : " + u.getAge());
		}

		Collections.sort(userList);

		System.out.println("排序List");
		for (User u : userList) {
			System.out.println(u.getName() + " : " + u.getAge());
		}
	}

	@Test
	public void test1() {
		String[] userArray = { "tom", "kate", "john", "anders", "harry", "jim", "lily", "cindy", "zhuzhen", "guolili" };
		Arrays.sort(userArray);
		for (String name : userArray)
			System.out.println(name);

		System.out.println("--------------------------");

		Collections.reverse(Arrays.asList(userArray));
		for (String name : userArray)
			System.out.println(name);
	}

	@Test
	public void test2() {
		// TODO Anders Zhu : SortedMap 、SortedSet、TreeMap 、TreeSet和Collections

		// 按照键值排序
		MyComparator<String> comparator = new MyComparator<String>();
		Map<String, String> sortMap = new TreeMap<String, String>(comparator);
		sortMap.put("键值", "a");
		sortMap.put("重庆", "a");
		sortMap.put("朱振", "b");
		sortMap.put("长大", "c");
		// 注意：每次对TreeMap进行put()时，TreeMap都会自动调用它的compare(key,Entry.key)
		// 按照key进行排序
		Collection<String> col = sortMap.keySet();
		Iterator<String> it = col.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}