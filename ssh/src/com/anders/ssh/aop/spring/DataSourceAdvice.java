package com.anders.ssh.aop.spring;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
	private static Log log = LogFactory.getLog(DataSourceAdvice.class);

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		log.debug("切入点: " + target.getClass().getName() + "类中的" + method.getName() + "方法");
		if (method.getName().startsWith("save") || method.getName().startsWith("update") || method.getName().startsWith("delete")) {
			log.debug("切换到: master");
			DataSourceSwitcher.setMaster();
		}
		else {
			log.debug("切换到: slave");
			DataSourceSwitcher.setSlave();
		}
	}

	@Override
	public void afterReturning(Object obj, Method method, Object[] args, Object target) throws Throwable {
	}

	public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass) {
		DataSourceSwitcher.setSlave();
		log.debug("出现异常，切换到：slave！");
	}
}
