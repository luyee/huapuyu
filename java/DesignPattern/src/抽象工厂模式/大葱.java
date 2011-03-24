package 抽象工厂模式;

public class 大葱 implements I蔬菜
{
	@Override
	public void grow()
	{
		System.out.println("大葱 : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("大葱 : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("大葱 : plant");
	}
}
