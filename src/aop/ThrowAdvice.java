package aop;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class ThrowAdvice implements ThrowsAdvice
{
	public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass)
	{
		System.out.println("异常: " + method.getName());
	}
}
