package 模版方法模式;

public class PlayChina extends PlayPES
{
	@Override
	protected void chooseShirt()
	{
		System.out.println("主场");
	}

	@Override
	protected void chooseTeam()
	{
		System.out.println("中国");
	}
}
