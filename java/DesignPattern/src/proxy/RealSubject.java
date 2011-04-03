package proxy;

public class RealSubject extends Subject
{
	public RealSubject()
	{
	}

	@Override
	public void request()
	{
		System.out.println("Real Subject");
	}
}
