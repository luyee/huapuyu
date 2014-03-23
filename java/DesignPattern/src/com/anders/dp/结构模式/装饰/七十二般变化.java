package com.anders.dp.结构模式.装饰;

public class 七十二般变化 implements 齐天大圣
{
	private 齐天大圣 monkey;

	public 七十二般变化(齐天大圣 monkey)
	{
		this.monkey = monkey;
	}

	@Override
	public void move()
	{
		monkey.move();
	}
}
