package aop.aspectj.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAspect
{
	// 切入点 * aspectj.. * .* (..) 表示任意类型的返回值 包名 包括子包 所有类 所有方法 任意参数
	@SuppressWarnings("unused")
	// @Pointcut("execution (* aspectj.PersonService.*(..))")
	@Pointcut("execution (* aspectj..*.*(..))")
	private void anyMethod()
	{
	}

	// 加args(name)后，则只对public void update(String name)这个方法进行控制，其他忽略
	@Before("anyMethod() && args(argName)")
	public void loggerBefore(String argName)
	{
		System.out.println("前置通知，参数为" + argName);
	}

	@AfterReturning(pointcut = "anyMethod()", returning = "result")
	public void loggerAfterReturning(String result)
	{
		System.out.println("后置通知，返回参数为" + result);
	}

	@AfterThrowing(pointcut = "anyMethod()", throwing = "e")
	public void loggerThrowing(Exception e)
	{
		System.out.println("异常通知，异常为" + e.getMessage());
	}

	@After("anyMethod()")
	public void loggerAfter()
	{
		System.out.println("最终通知");
	}

	@Around("anyMethod()")
	public Object loggerAround(ProceedingJoinPoint pjp) throws Throwable
	{
		System.out.println("环绕通知-前");
		Object object = pjp.proceed();
		System.out.println("环绕通知-后");
		return object;
	}
}
