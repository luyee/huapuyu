package com.anders.dp.简单工厂.大话设计模式;

public abstract class Operation<T> {
	protected T a;
	protected T b;

	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public T getB() {
		return b;
	}

	public void setB(T b) {
		this.b = b;
	}

	abstract T operator();
}
