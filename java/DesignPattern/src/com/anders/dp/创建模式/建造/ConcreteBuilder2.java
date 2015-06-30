package com.anders.dp.创建模式.建造;

public class ConcreteBuilder2 extends Builder {
	@Override
	public void 内容() {
		System.out.println(this.toString() + " : 内容2");
	}

	@Override
	public void 主题() {
		System.out.println(this.toString() + " : 主题2");
	}
}
