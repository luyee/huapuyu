package com.anders.dp.行为模式.观察者;

public interface Subject
{
	public void attach(Observer observer);

	public void detach(Observer observer);

	public void notifyObservers();
}
