package 代理模式.动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxySubject implements InvocationHandler
{
	private RealSubject realSubject; // 以真实角色作为代理角色的属性

	public ProxySubject()
	{
	}

	private void preRequest()
	{
		System.out.println("preRequest.");
	}

	private void postRequest()
	{
		System.out.println("postRequest.");
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		if (null == realSubject)
			realSubject = new RealSubject();

		this.preRequest();

		method.invoke(realSubject, args);

		this.postRequest();

		return null;
	}
}
