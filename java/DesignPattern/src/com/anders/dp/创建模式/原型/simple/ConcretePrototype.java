package com.anders.dp.创建模式.原型.simple;

public class ConcretePrototype implements Prototype
{
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
