package com.anders.dp.结构模式.桥梁;

public class 邮件 extends Implementor {
	@Override
	public void send() {
		System.out.println("发送邮件");
	}
}
