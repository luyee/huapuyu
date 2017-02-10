package com.anders.dp.创建模式.单例;

public class 懒汉 {
	private volatile static 懒汉 singleton = null;

	private 懒汉() {
	}

	public static final 懒汉 getInstance() {
		if (null == singleton) {
			synchronized (懒汉.class) {
				if (null == singleton) {
					singleton = new 懒汉();
				}
			}
		}

		return singleton;
	}
}
