package com.anders.dp.结构模式.外观;

public class Sensor
{
	public void activate()
	{
		System.out.println("启动感应器");
	}

	public void deactivate()
	{
		System.out.println("关闭感应器");
	}

	public void trigger()
	{
		System.out.println("触发感应器");
	}
}
