package com.anders.ssh.common;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class AccountBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return Account.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");

		if (StringUtils.hasText(id)) {
			builder.addPropertyValue("id", id);
		}
		if (StringUtils.hasText(name)) {
			builder.addPropertyValue("name", name);
		}
	}
}
