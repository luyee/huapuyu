package com.anders.dp.行为模式.策略;

public class Context
{
	private Strategy strategy;

	public Context(Strategy strategy)
	{
		this.strategy = strategy;
	}

	public void doSomething()
	{
		this.strategy.doSomething();
	}
}
