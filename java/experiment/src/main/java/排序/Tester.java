package 排序;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}