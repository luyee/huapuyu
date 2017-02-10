package com.anders.dp.结构模式.外观;

public class Alarm
{
	public void activate()
	{
		System.out.println("启动警报器");
	}

	public void deactivate()
	{
		System.out.println("关闭警报器");
	}

	public void ring()
	{
		System.out.println("拉响警报器");
	}

	public void stopRing()
	{
		System.out.println("停掉警报器");
	}
}
