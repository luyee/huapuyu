package com.anders.vote.interceptor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

public class SpringMessageSourceInterceptor extends AbstractInterceptor {

	protected Logger logger = LoggerFactory
			.getLogger(SpringMessageSourceInterceptor.class);

	private static final long serialVersionUID = -1248711503800167450L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		Locale locale = actionContext.getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message",
				locale);
		ResourceBundleModel rbm = new ResourceBundleModel(resourceBundle,
				new BeansWrapper());
		actionContext.put("resource", rbm);

		if (logger.isDebugEnabled())
			logger.debug(invocation.getAction().getClass().getName()
					+ " is binded");

		return invocation.invoke();
	}
}
