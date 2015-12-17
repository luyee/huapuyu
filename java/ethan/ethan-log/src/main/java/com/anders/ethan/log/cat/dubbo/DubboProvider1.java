package com.anders.ethan.log.cat.dubbo;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProvider1 {
	public static void main(String[] args) throws IOException {

		new ClassPathXmlApplicationContext("classpath:dubbo-provider.xml");

		System.in.read();
	}
}
