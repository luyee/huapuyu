package com.anders.ssh.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass
{
	public static void main(String[] args)
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		ZhuRongbao zhuRongbao = (ZhuRongbao) ctx.getBean("zhuRongbao");
		System.out.println(zhuRongbao.toString());
	}
}
