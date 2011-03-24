package 抽象工厂模式;

public class 江苏 implements I省份
{
	@Override
	public I水果 factoryFruit()
	{
		return new 桔子();
	}

	@Override
	public I蔬菜 factoryVegetable()
	{
		return new 青菜();
	}
}
