package com.anders.vote.action;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport implements SessionAware, RequestAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Map<String, Object> session;
	protected Map<String, Object> request;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	protected void printBeanDefinitionNames() {
		ApplicationContext applicationContext = (ApplicationContext) ActionContext.getContext().getApplication().get(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		logger.debug("BeanDefinitionCount : " + applicationContext.getBeanDefinitionCount());
		String[] names = applicationContext.getBeanDefinitionNames();
		if (ArrayUtils.isNotEmpty(names)) {
			logger.debug("BeanDefinitionNames Begin...");
			for (String name : names) {
				logger.debug("BeanDefinitionNames : " + name);
			}
			logger.debug("BeanDefinitionNames End...");
		}
	}

	protected void printSessionNames() {
		logger.debug("SessionNames Begin...");
		for (Iterator<String> iterator = session.keySet().iterator(); iterator.hasNext();) {
			logger.debug("SessionNames : " + iterator.next());
		}
		logger.debug("SessionNames End...");
	}
	
	protected void printRequestNames() {
		logger.debug("RequestNames Begin...");
		for (Iterator<String> iterator = request.keySet().iterator(); iterator.hasNext();) {
			logger.debug("RequestNames : " + iterator.next());
		}
		logger.debug("RequestNames End...");
	}

}
