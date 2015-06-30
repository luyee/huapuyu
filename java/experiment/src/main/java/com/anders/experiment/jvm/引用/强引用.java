package com.anders.experiment.jvm.引用;

public class 强引用 {

	public static void main(String[] args) {
		User user1 = new User();
		user1.setName("zhuzhen");
		User user2 = user1;
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user1.getName());
		System.out.println(user2.getName());
	}

}
