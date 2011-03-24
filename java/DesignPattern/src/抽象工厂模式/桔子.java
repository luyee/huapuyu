package 抽象工厂模式;

public class 桔子 implements I水果
{
	@Override
	public void grow()
	{
		System.out.println("桔子 : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("桔子 : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("桔子 : plant");
	}
}
