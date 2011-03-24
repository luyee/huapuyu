package 抽象工厂模式;

public class 苹果 implements I水果
{
	@Override
	public void grow()
	{
		System.out.println("苹果 : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("苹果 : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("苹果 : plant");
	}
}
