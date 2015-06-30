package com.anders.ssh.taskexecutor;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class TaskExecutorTest
{
	@Resource
	private TaskExecutor taskExecutor;

	@Test
	public void test1()
	{
		for (int i = 0; i < 25; i++)
		{
			// taskExecutor.execute(new Thread1("thread" + i));
			taskExecutor.execute(new Thread2("thread" + i));
		}
	}
}

class Thread1 implements Runnable
{
	private String name;

	public Thread1(String name)
	{
		this.name = name;
	}

	@Override
	public void run()
	{
		for (long i = 0; i < 5000000; i++)
		{
			int j = 5 / 3;
		}
		System.out.println(name);
	}
}

class Thread2 extends Thread
{
	private String name;

	public Thread2(String name)
	{
		this.name = name;
		setDaemon(false);
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 50000000; i++)
		{
			int j = 5 / 3;
		}
		System.out.println(name);
		super.run();
	}
}
