package 抽象工厂模式;

public class 皇家马德里 implements I俱乐部
{
	@Override
	public I前锋 factory前锋()
	{
		return new C罗();
	}

	@Override
	public I球场 factory球场()
	{
		return new 伯纳乌();
	}
}
