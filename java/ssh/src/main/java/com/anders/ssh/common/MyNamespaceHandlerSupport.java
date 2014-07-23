package com.anders.ssh.common;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandlerSupport extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("account", new AccountBeanDefinitionParser());
	}
}
