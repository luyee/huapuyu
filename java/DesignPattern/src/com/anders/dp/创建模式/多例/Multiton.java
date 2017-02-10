package com.anders.dp.创建模式.多例;

public class Multiton
{
	private static Multiton m1 = new Multiton();
	private static Multiton m2 = new Multiton();

	private Multiton()
	{
	}

	public static Multiton getInstance(int whichOne)
	{
		if (1 == whichOne)
		{
			return m1;
		}
		else
		{
			return m2;
		}
	}
}
