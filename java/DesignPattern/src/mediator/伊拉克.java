package mediator;

public class 伊拉克 extends 国家
{

	public 伊拉克(联合国机构 mediator)
	{
		super(mediator);
	}

	public void Declare(String message)
	{
		mediator.Declare(message, this);
	}

	public void GetMessage(String message)
	{
		System.out.println("伊拉克获得对方信息：" + message);
	}
}
