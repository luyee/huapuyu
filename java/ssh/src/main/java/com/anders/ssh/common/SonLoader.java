package com.anders.ssh.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SonLoader implements ApplicationContextAware {

	ApplicationContext parentApplicationContext;
	ConfigurableApplicationContext sonContext;

	public void load() {
		sonContext = new ClassPathXmlApplicationContext("classpath:spring-common-son-test.xml");
		sonContext.setParent(parentApplicationContext);
		sonContext.refresh();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.parentApplicationContext = applicationContext;
	}

	public void parentPrint() {
		ParentClass parentClass = (ParentClass) sonContext.getBean("parentClass");
		parentClass.print();
	}

	public void sonPrint() {
		SonClass sonClass = (SonClass) sonContext.getBean("sonClass");
		sonClass.print();
	}
}
