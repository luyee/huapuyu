package com.anders.dp.结构模式.桥梁;

/*
 * 修正抽象化
 */
public class 邮件Abstraction extends Abstraction {
	@Override
	public void sendMsg() {
		implementor.send();
		System.out.println("收到请回复邮件");
	}
}
