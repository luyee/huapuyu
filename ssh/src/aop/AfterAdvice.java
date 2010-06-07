package aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class AfterAdvice implements AfterReturningAdvice
{
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable
	{
		System.out.println("之后: " + method.getName());
	}
}
