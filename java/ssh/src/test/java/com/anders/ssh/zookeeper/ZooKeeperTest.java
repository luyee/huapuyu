package com.anders.ssh.zookeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-zookeeper.xml" })
public class ZooKeeperTest {
	@Test
	public void test() {
		Logger logger = (Logger) LoggerFactory.getLogger("root");
		// String levelStr = logger.getLevel().levelStr;
	}
}
