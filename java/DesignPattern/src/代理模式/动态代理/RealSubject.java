package 代理模式.动态代理;

public class RealSubject implements Subject
{
	public RealSubject()
	{
	}

	public void request()
	{
		System.out.println("From real subject.");
	}
}
