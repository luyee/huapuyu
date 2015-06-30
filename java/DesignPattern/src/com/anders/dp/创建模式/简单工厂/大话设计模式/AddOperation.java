package com.anders.dp.创建模式.简单工厂.大话设计模式;

public class AddOperation extends Operation<Integer> {
	@Override
	Integer operator() {
		return a + b;
	}
}
