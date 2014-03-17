package com.anders.dp.命令;

public class Invoker
{
	private Command command;

	public Invoker(Command command)
	{
		this.command = command;
	}

	public void action()
	{
		command.execute();
	}
}
