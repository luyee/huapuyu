package com.anders.ssh.aop.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainTest {
	public static void main(String[] args) {
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		// BeanFactory bf = new XmlBeanFactory(new
		// ClassPathResource("spring.xml"));

		ProxyTarget pt = (ProxyTarget) ctx.getBean("proxyTargetFactory");
		pt.ShowMessage();
		pt.ShowName();
		pt.printMessage();

		ProxyTarget pt1 = (ProxyTarget) ctx.getBean("autoProxyTarget");
		pt1.ShowMessage();
		pt1.ShowName();
		pt1.printMessage();
	}
}
