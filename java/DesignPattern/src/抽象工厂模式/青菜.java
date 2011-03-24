package 抽象工厂模式;

public class 青菜 implements I蔬菜
{
	@Override
	public void grow()
	{
		System.out.println("青菜 : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("青菜 : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("青菜 : plant");
	}
}
