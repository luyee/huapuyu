package com.anders.ssh.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-taskexecutor-test.xml" })
public class TaskExecutorTest {
	@Resource
	private TaskExecutor taskExecutor;

	@Test
	public void test() {
		for (int i = 0; i < 10; i++) {
			taskExecutor.execute(new Thread1());
			taskExecutor.execute(new Thread2());
		}
	}
}

class Thread1 implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Thread1.class);

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			LOG.debug(String.valueOf(i));
		}
	}
}

class Thread2 extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Thread2.class);

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			LOG.debug(String.valueOf(i));
		}
	}
}
