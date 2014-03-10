package com.anders.dp.单例;

public class 饿汉 {
	private static final 饿汉 singleton = new 饿汉();

	private 饿汉() {
	}

	public static 饿汉 getInstance() {
		return singleton;
	}
}
