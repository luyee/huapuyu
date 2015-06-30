package com.anders.dp.行为模式.观察者;

import java.util.Enumeration;
import java.util.Vector;

public class ConcreteSubject implements Subject
{
	private Vector<Observer> vector = new Vector<Observer>();

	@Override
	public void attach(Observer observer)
	{
		vector.addElement(observer);
	}

	@Override
	public void detach(Observer observer)
	{
		vector.removeElement(observer);
	}

	@Override
	public void notifyObservers()
	{
		Enumeration<Observer> enumeration = observers();
		while (enumeration.hasMoreElements())
		{
			((Observer) enumeration.nextElement()).update();
		}
	}

	public Enumeration<Observer> observers()
	{
		return ((Vector<Observer>) vector.clone()).elements();
	}
}
