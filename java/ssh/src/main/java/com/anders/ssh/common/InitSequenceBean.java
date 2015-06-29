package com.anders.ssh.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;

// 执行顺序如下
// InitSequenceBean: constructor
// InitSequenceBean: postConstruct
// InitSequenceBean: afterPropertiesSet
// InitSequenceBean: init-method
public class InitSequenceBean implements InitializingBean {
	public InitSequenceBean() {
		System.out.println("InitSequenceBean: constructor");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("InitSequenceBean: postConstruct");
	}

	// 需要在spring文件中该bean的配置上添加init-method="initMethod"
	public void initMethod() {
		System.out.println("InitSequenceBean: init-method");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("InitSequenceBean: afterPropertiesSet");
	}
}
