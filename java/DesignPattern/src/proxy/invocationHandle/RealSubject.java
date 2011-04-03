package proxy.invocationHandle;

public class RealSubject implements Subject
{
	public RealSubject()
	{
	}

	public void request()
	{
		System.out.println("Real Subject");
	}
}
