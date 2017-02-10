package com.anders.dp.行为模式.备忘录;

/*
 * 纪念物，令人回忆的东西，纪念品，假设为：游戏记录
 */
public class Memento
{
	private String state;

	public Memento(String state)
	{
		this.state = state;
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
