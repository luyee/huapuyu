package com.anders.ssh.others;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-test.xml");
		MyApplicationContextAware aca = (MyApplicationContextAware) ctx.getBean("myApplicationContextAware");
		aca.printCtx();
	}
}
