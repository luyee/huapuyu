package com.anders.dp.简单工厂.大话设计模式;

import org.junit.Assert;
import org.junit.Test;

import com.anders.dp.简单工厂.大话设计模式.OperationFactory.Operator;


public class Tester {
	@Test
	public void test() {
		Operation<Integer> addOperation = OperationFactory.getOperation(Operator.ADD);
		addOperation.setA(1);
		addOperation.setB(2);
		Assert.assertEquals(3, addOperation.operator().intValue());

		Operation<Integer> subOperation = OperationFactory.getOperation(Operator.SUB);
		subOperation.setA(1);
		subOperation.setB(2);
		Assert.assertEquals(-1, subOperation.operator().intValue());
	}
}
