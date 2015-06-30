package com.anders.dp.创建模式.工厂方法;

public class 奥迪工厂类 implements IFactory {
	public ICar factory() {
		return new 奥迪();
	}
}
