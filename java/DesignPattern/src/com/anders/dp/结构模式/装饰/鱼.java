package com.anders.dp.结构模式.装饰;

public class 鱼 extends 七十二般变化 {

	public 鱼(齐天大圣 monkey) {
		super(monkey);
		System.out.println("做些其他事情1");
	}

	public void move() {
		System.out.println("做些其他事情2");
		super.move();
		System.out.println("做些其他事情3");
	}
}
