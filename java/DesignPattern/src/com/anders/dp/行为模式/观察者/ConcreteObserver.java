package com.anders.dp.行为模式.观察者;

public class ConcreteObserver implements Observer
{
	@Override
	public void update()
	{
		System.out.println("I am notified");
	}
}
