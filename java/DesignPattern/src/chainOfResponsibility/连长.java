package chainOfResponsibility;

public class 连长 extends Handler
{
	public 连长(String name)
	{
		this.name = name;
	}

	@Override
	public void handleRequest(String request)
	{
		if ("集合全连士兵".equals(request))
		{
			System.out.println(this.name + "可以" + request);
		}
		else
		{
			System.out.println(this.name + "不可以" + request + "，由上级" + nextHandler.getName() + "发布命令");
			nextHandler.handleRequest(request);
		}
	}
}
