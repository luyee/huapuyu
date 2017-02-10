package com.anders.ssh.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;

public class ServiceLocator {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(ServiceLocator.class);

	private static ServiceLocator locator = null;

	private static BeanFactory factory = null;

	public BeanFactory getFactory() {
		if (factory == null) {
			// here just log out
			LOGGER.error("ServiceLocator.factory is null maybe not config InitSystemListener" + " correctly in web.xml");
		}
		return factory;
	}

	private ServiceLocator() {
	}

	public void setFactory(BeanFactory factory) {
		setToFactory(factory);
	}

	private static void setToFactory(BeanFactory factory) {
		ServiceLocator.factory = factory;
	}

	public static ServiceLocator getInstance() {
		if (locator == null) {
			locator = new ServiceLocator();
		}
		return locator;
	}

	public Object getBean(String beanName) {
		return getFactory().getBean(beanName);
	}

	// public JavaMailSender getJavaMailSender() {
	// return (JavaMailSender) getBean("mailSender");
	// }
	//
	// public Configuration getFreeMarkerConfig() {
	// return (Configuration) getBean("freeMarkerConfigurer");
	// }

}
