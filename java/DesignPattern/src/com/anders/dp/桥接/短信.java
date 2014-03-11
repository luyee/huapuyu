package com.anders.dp.桥接;

public class 短信 extends Implementor {
	@Override
	public void send() {
		System.out.println("发送短信");
	}
}
