package decorator;

public class 大圣本尊 implements 齐天大圣
{
	public 大圣本尊()
	{
		System.out.println("我是大圣本尊");
	}

	@Override
	public void move()
	{
		System.out.println("大圣本尊移动");
	}

}
