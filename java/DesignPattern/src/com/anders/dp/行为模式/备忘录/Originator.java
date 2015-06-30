package com.anders.dp.行为模式.备忘录;

/*
 * 创始人，发起人
 */
public class Originator
{
	private String state;

	public Memento createMemento()
	{
		return new Memento(state);
	}

	public void restoreMemento(Memento memento)
	{
		this.state = memento.getState();
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
}
