package com.anders.ssh.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitSystemListener implements ServletContextListener {

	private static final long serialVersionUID = 8920435030558656346L;

	public void contextInitialized(ServletContextEvent event) {

		ApplicationContext factory = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		try {
			ServiceLocator.getInstance().setFactory(factory);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServiceLocator.getInstance().setFactory(null);
	}
}
