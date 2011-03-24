package 抽象工厂模式;

public class 阿尔卑球场 implements I球场
{
	@Override
	public void 名称()
	{
		System.out.println("阿尔卑球场");
	}

	@Override
	public void 地点()
	{
		System.out.println("都灵");
	}
}
