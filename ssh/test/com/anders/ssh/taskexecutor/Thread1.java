package com.anders.ssh.taskexecutor;

public class Thread1 implements Runnable
{
	private String name;

	public Thread1(String name)
	{
		this.name = name;
	}

	@Override
	public void run()
	{
		for (long i = 0; i < 5000000; i++)
		{
			int j = 5 / 3;
		}
		System.out.println(name);
	}
}
