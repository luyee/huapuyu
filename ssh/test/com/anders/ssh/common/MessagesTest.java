package com.anders.ssh.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class MessagesTest extends UnitilsJUnit4 {
	@Autowired
	private Messages messages;

	@Test
	public void test1() {
		System.out.println(System.getProperty("user.language"));
		System.out.println(System.getProperty("user.country"));
		System.out.println(System.getProperties());
		System.out.println(messages.getMessage("main.title"));
	}
}
