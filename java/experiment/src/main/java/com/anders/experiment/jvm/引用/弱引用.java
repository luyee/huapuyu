package com.anders.experiment.jvm.引用;

import java.lang.ref.WeakReference;

public class 弱引用 {

	public static void main(String[] args) {
		User user1 = new User();
		user1.setName("zhuzhen");
		WeakReference<User> user2 = new WeakReference<User>(user1);
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user1.getName());
		System.out.println(user2.get().getName());
	}

}
