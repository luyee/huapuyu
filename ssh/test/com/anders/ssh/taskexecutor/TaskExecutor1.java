package com.anders.ssh.taskexecutor;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutor1
{
	@Resource
	private TaskExecutor taskExecutor;

	public void printMsg()
	{
		for (int i = 0; i < 25; i++)
		{
			taskExecutor.execute(new Thread1("thread" + i));
		}
	}
}
