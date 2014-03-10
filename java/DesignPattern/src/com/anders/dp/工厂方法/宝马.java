package com.anders.dp.工厂方法;

public class 宝马 implements ICar {
	@Override
	public void 启动() {
		System.out.println("宝马启动");
	}

	@Override
	public void 停止() {
		System.out.println("宝马停止");
	}
}
