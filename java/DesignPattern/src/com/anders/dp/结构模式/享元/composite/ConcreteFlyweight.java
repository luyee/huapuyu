package com.anders.dp.结构模式.享元.composite;

public class ConcreteFlyweight extends Flyweight
{
	private Character intrinsicState = null;

	public ConcreteFlyweight(Character state)
	{
		this.intrinsicState = state;
	}

	@Override
	public void operation(String state)
	{
		System.out.println("Intrinsic State = " + intrinsicState + ", Extrinsic State = " + state + ";");
	}
}
