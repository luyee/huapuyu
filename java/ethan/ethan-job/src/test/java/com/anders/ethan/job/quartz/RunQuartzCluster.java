package com.anders.ethan.job.quartz;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunQuartzCluster {
	@Test
	public void testSave() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:quartz-cluster.xml");

		System.in.read();
	}
}
