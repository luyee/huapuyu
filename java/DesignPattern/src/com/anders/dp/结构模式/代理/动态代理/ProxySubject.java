package com.anders.dp.结构模式.代理.动态代理;

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
		System.out.println("PreRequest");
	}

	private void postRequest()
	{
		System.out.println("PostRequest");
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		this.preRequest();

		if (null == realSubject)
			realSubject = new RealSubject();

		Object object = method.invoke(realSubject, args);

		this.postRequest();

		return object;
	}
}
