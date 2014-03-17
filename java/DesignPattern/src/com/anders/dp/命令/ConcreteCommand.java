package com.anders.dp.命令;

public class ConcreteCommand implements Command
{
	private Receiver receiver;

	public ConcreteCommand(Receiver receiver)
	{
		this.receiver = receiver;
	}

	@Override
	public void execute()
	{
		receiver.action();
	}
}
