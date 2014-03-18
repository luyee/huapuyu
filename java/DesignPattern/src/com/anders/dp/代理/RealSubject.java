package com.anders.dp.代理;

public class RealSubject extends Subject
{
	public RealSubject()
	{
	}

	@Override
	public void request()
	{
		System.out.println("Real Subject");
	}
}
