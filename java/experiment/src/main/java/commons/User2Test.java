package commons;

import java.util.ArrayList;
import java.util.List;

public class User2Test {
	public static void main(String[] args) {
		User2 user2 = new User2();
		user2.setId(1L);
		user2.setName("zhuzhen");

		User3 user3 = new User3();
		user3.setId(2L);
		user3.setName("guolili");

		List<User3> users = new ArrayList<User3>();
		users.add(user3);

		user2.setList(users);
		System.out.println(user2);
	}
}
