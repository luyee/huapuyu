package com.anders.dp.适配器.类的适配器;

public class Adapter extends Adaptee implements Target {
	@Override
	public void 瓦工() {
		System.out.println("装修队长的瓦工");
	}

	@Override
	public void 木工() {
		System.out.println("装修队长的木工");
	}
}
