package com.anders.dp.创建模式.抽象工厂;

public class 伯纳乌球场 implements I球场 {
	@Override
	public void 名称() {
		System.out.println("伯纳乌");
	}

	@Override
	public void 地点() {
		System.out.println("马德里");
	}
}
