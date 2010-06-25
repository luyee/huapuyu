package aop.spring;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdvice implements MethodBeforeAdvice
{
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable
	{
		System.out.println("之前: " + method.getName());
	}
}
