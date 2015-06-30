package com.anders.experiment.spring;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperTester {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object object = Class.forName("spring.impl.UserServiceImpl").newInstance();
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);

		User user = new User();
		user.setId(1L);
		user.setName("zhuzhen");

		beanWrapper.setPropertyValue("user", user);
		beanWrapper.setPropertyValue("name", "guolili");

		System.out.println(((User) beanWrapper.getPropertyValue("user")).getName());
		System.out.println(beanWrapper.getPropertyValue("name"));

	}
}
