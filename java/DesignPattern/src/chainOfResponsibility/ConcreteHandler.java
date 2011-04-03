package chainOfResponsibility;

public class ConcreteHandler extends Handler
{
	@Override
	public void handleRequest()
	{
		if (getSuccessor() != null)
		{
			getSuccessor().handleRequest();
		}
		else
		{
			System.out.println("the request is handle here");
		}
	}
}
