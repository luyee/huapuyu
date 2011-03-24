package 代理模式;

public class MainTest
{
	public static void main(String[] args)
	{
		Subject sub = new ProxySubject();
		sub.request();
	}
}
