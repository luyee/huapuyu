package com.anders.ssh.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitSystemListener implements ServletContextListener {

	private final Logger LOG = LoggerFactory.getLogger(InitSystemListener.class);

	public void contextInitialized(ServletContextEvent event) {

		ApplicationContext factory = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

		String[] names = factory.getBeanDefinitionNames();
		System.out.println("********************************************************************");
		System.out.println("***                         Spring Bean                          ***");
		System.out.println("********************************************************************");
		int i = 1;
		for (String name : names) {
			System.out.println(i++ + "." + name);
		}
		System.out.println("********************************************************************");
		System.out.println("***                         Spring Bean                          ***");
		System.out.println("********************************************************************");

		try {
			ServiceLocator.getInstance().setFactory(factory);
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServiceLocator.getInstance().setFactory(null);
	}
}
