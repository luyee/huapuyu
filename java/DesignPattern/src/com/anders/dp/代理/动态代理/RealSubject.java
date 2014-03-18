package com.anders.dp.代理.动态代理;

public class RealSubject implements Subject
{
	public RealSubject()
	{
	}

	public void request()
	{
		System.out.println("Real Subject");
	}
}
