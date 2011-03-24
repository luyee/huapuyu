package 抽象工厂模式;

public class 山东 implements I省份
{
	@Override
	public I水果 factoryFruit()
	{
		return new 苹果();
	}

	@Override
	public I蔬菜 factoryVegetable()
	{

		return new 大葱();
	}
}
