package 代理模式;

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
