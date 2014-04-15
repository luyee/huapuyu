package com.anders.ssh.aop.spring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-aop-test.xml" })
public class AopTest {
	// @Resource
	// private ProxyFactoryBean proxyFactoryBean;
	@Resource
	private ProxyTarget autoProxyTarget;

	@Test
	public void testDelete() {
		// proxyFactoryBean.ShowMessage();
		// proxyFactoryBean.ShowName();
		// proxyFactoryBean.printMessage();

		autoProxyTarget.ShowMessage();
		autoProxyTarget.ShowName();
		autoProxyTarget.printMessage();
	}

}
