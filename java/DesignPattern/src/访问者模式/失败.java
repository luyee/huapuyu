package 访问者模式;

public class 失败 extends Action
{
	@Override
	public void getManConclusion(男人 man)
	{
		System.out.println("男人：失败");
	}

	@Override
	public void getWomanConclusion(女人 woman)
	{
		System.out.println("女人：失败");
	}
}
