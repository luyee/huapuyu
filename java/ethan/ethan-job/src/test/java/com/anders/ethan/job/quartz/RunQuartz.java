package com.anders.ethan.job.quartz;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunQuartz {
	@Test
	public void testSave() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:quartz.xml");

		System.in.read();
	}
}
