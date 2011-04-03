package mediator;

public class 美国 extends 国家
{
	public 美国(联合国机构 mediator)
	{
		super(mediator);
	}

	public void Declare(String message)
	{
		mediator.Declare(message, this);
	}

	public void GetMessage(String message)
	{
		System.out.println("美国获得对方信息：" + message);
	}
}
