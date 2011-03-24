package 工厂方法模式;

public class 奥迪工厂类 implements I抽象工厂类
{
	public ICar factory()
	{
		return new 奥迪();
	}
}
