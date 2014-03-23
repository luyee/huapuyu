package com.anders.dp.创建模式.简单工厂;

public class 奔驰 implements ICar {
	@Override
	public void 启动() {
		System.out.println("奔驰启动");
	}

	@Override
	public void 停止() {
		System.out.println("奔驰停止");
	}
}
