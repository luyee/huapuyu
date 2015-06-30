package com.anders.experiment.jvm.引用;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class 虚引用 {

	public static void main(String[] args) {
		User user1 = new User();
		user1.setName("zhuzhen");

		ReferenceQueue<User> referenceQueue = new ReferenceQueue<User>();
		PhantomReference<User> user2 = new PhantomReference<User>(user1, referenceQueue);
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user1.getName());
		System.out.println(user2.get());
		// 当对象被回收时， 虚拟机会自动将这个对象插入到 ReferenceQueue中
		System.out.println(user2.isEnqueued());
	}

}
