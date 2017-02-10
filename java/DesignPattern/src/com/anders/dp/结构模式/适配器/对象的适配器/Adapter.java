package com.anders.dp.结构模式.适配器.对象的适配器;

public class Adapter implements Target {
	private Adaptee adaptee;

	public Adapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void 瓦工() {
		System.out.println("装修队长的瓦工");
	}

	@Override
	public void 木工() {
		System.out.println("装修队长的木工");
	}

	@Override
	public void 漆工() {
		this.adaptee.漆工();
	}
}