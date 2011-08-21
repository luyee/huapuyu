package com.anders.ssh.aop.spring;

public class ManualProxyTarget implements IProxyTarget
{
	public void ShowMessage()
	{
		System.out.println(this.getClass().getName() + " : ShowMessage");
		// throw new RuntimeException("错误");
	}

	@Override
	public void ShowName()
	{
		System.out.println(this.getClass().getName() + " : ShowName");
	}

	@Override
	public void printMessage()
	{
		System.out.println(this.getClass().getName() + " : printMessage");

	}
}
