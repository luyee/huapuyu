package com.anders.ssh.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServer {
 public static void main(String[] args) {
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring-rmi.xml");
}
}
