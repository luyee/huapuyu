package mediator;

public abstract class 国家
{
	protected 联合国机构 mediator;

	public 国家(联合国机构 mediator)
	{
		this.mediator = mediator;
	}
}
