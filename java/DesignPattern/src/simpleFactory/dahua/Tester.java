package simpleFactory.dahua;

import org.junit.Assert;
import org.junit.Test;

import simpleFactory.dahua.OperationFactory.Operator;

public class Tester
{
	@Test
	public void test()
	{
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
