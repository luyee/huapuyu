package factoryMethod;

public class 宝马工厂类 implements I抽象工厂类
{
	public ICar factory()
	{
		return new 宝马();
	}
}
