package 工厂方法模式;

public class OrangeGardener implements IFruitGardener
{
	public IFruit factory()
	{
		return new Orange();
	}
}
