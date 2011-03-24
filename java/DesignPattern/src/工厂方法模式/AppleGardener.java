package 工厂方法模式;

public class AppleGardener implements IFruitGardener
{
	public IFruit factory()
	{
		return new Apple();
	}
}
