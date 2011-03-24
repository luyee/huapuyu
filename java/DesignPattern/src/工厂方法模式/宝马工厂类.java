package 工厂方法模式;

public class 宝马工厂类 implements 抽象工厂类
{
	public ICar factory()
	{
		return new 宝马();
	}
}
