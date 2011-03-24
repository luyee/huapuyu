package 代理模式;

public class RealSubject extends Subject
{
	public RealSubject()
	{
	}

	public void request()
	{
		System.out.println("From real subject.");
	}
}
