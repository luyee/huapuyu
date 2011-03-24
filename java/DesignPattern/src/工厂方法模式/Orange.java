package 工厂方法模式;

public class Orange implements IFruit
{
	@Override
	public void grow()
	{
		System.out.println("orange : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("orange : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("orange : plant");
	}
}
