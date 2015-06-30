package com.anders.experiment.jvm.引用;

import java.lang.ref.SoftReference;

public class 软引用 {

	public static void main(String[] args) {
		User user1 = new User();
		user1.setName("zhuzhen");
		SoftReference<User> user2 = new SoftReference<User>(user1);
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user1.getName());
		System.out.println(user2.get().getName());
	}

}
