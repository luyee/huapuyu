package com.anders.dp.创建模式.原型.register;

public class ConcretePrototype implements Prototype {
	@Override
	public synchronized Object clone() {
		Prototype prototype = null;

		try {
			prototype = (Prototype) super.clone();
			return prototype;
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return prototype;
	}
}
