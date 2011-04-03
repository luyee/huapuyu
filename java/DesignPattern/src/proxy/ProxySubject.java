package proxy;

public class ProxySubject extends Subject
{
	private RealSubject realSubject; // 以真实角色作为代理角色的属性

	public ProxySubject()
	{
	}

	// 该方法封装了真实对象的request方法
	@Override
	public void request()
	{
		preRequest();

		if (null == realSubject)
			realSubject = new RealSubject();

		realSubject.request(); // 此处执行真实对象的request方法

		postRequest();
	}

	private void preRequest()
	{
		System.out.println("preRequest.");
	}

	private void postRequest()
	{
		System.out.println("postRequest.");
	}
}
