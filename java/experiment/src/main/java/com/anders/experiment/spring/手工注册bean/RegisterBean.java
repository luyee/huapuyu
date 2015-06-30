package com.anders.experiment.spring.手工注册bean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RegisterBean {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/anders/experiment/spring/手工注册bean/applicationContext.xml");

		User user = new User();
		user.setAge(32L);

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(user.getClass());
		beanDefinitionBuilder.addPropertyValue("name", "zhuzhen");

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ctx;
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) configurableApplicationContext
				.getBeanFactory();
		bf.registerBeanDefinition("user",
				beanDefinitionBuilder.getRawBeanDefinition());

		User u = (User) bf.getBean("user");
		System.out.println(u.getName());
		System.out.println(u.getAge());
	}
}

class User {
	private String name;

	private Long age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
}
