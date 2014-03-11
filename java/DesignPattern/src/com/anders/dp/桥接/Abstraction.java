package com.anders.dp.桥接;

/*
 * 抽象化
 */
public abstract class Abstraction {
	protected Implementor implementor;

	public void setImplementor(Implementor implementor) {
		this.implementor = implementor;
	}

	public void sendMsg() {
		implementor.send();
	}
}
