package com.anders.ssh.taskexecutor;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test/junit/task/executor/spring.xml" })
public class Tester
{
	@Resource
	private TaskExecutor1 taskExecutor1;

	@Test
	public void test1()
	{
		taskExecutor1.printMsg();
	}
}
