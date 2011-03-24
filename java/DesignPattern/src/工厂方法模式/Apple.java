package 工厂方法模式;

public class Apple implements IFruit
{
	@Override
	public void grow()
	{
		System.out.println("apple : grow");
	}

	@Override
	public void harvest()
	{
		System.out.println("apple : harvest");
	}

	@Override
	public void plant()
	{
		System.out.println("apple : plant");
	}
}
