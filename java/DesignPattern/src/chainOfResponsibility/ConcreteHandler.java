package chainOfResponsibility;

public class ConcreteHandler extends Handler
{
	@Override
	public void handleRequest(String request)
	{
		if (getNextHandler() != null)
		{
			System.out.println("无法处理，交给下一步处理");
			nextHandler.handleRequest(request);
		}
		else
		{
			System.out.println("处理");
		}
	}
}
