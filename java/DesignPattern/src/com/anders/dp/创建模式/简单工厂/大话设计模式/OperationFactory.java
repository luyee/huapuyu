package com.anders.dp.创建模式.简单工厂.大话设计模式;

public class OperationFactory {
	public enum Operator {
		ADD, SUB
	}

	public static Operation<Integer> getOperation(Operator operator) {
		switch (operator) {
		case ADD:
			return new AddOperation();
		case SUB:
			return new SubOperation();
		default:
			throw new RuntimeException("没有默认的操作");
		}
	}
}
