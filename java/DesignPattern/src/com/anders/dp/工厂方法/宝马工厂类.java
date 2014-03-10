package com.anders.dp.工厂方法;

public class 宝马工厂类 implements IFactory {
	public ICar factory() {
		return new 宝马();
	}
}
