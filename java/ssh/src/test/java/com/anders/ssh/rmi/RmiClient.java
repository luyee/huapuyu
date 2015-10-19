package com.anders.ssh.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiClient {
 public static void main(String[] args) {
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-remote-test.xml");
	
	AndersRmi andersRmi = (AndersRmi) context.getBean("andersRmi");
	System.out.println(andersRmi.getRmi());
}
}
