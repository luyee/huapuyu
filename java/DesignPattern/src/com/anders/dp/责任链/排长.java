package com.anders.dp.责任链;

public class 排长 extends Handler
{
	public 排长(String name)
	{
		this.name = name;
	}

	@Override
	public void handleRequest(String request)
	{
		if ("集合全排士兵".equals(request))
		{
			System.out.println(this.name + "可以" + request);
		}
		else
		{
			System.out.println(this.name + "不可以" + request + "，由上级" + nextHandler.getName() + "发布命令");
			nextHandler.handleRequest(request);
		}
	}
}
