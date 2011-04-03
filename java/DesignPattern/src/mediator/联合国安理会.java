package mediator;

public class 联合国安理会 extends 联合国机构
{
	private 美国 usa;
	private 伊拉克 iraq;

	@Override
	public void Declare(String message, 国家 colleague)
	{
		if (colleague.equals(usa))
		{
			iraq.GetMessage(message);
		}
		else
		{
			usa.GetMessage(message);
		}
	}

	public 美国 getUsa()
	{
		return usa;
	}

	public void setUsa(美国 usa)
	{
		this.usa = usa;
	}

	public 伊拉克 getIraq()
	{
		return iraq;
	}

	public void setIraq(伊拉克 iraq)
	{
		this.iraq = iraq;
	}

}
