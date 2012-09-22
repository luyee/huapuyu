package com.anders.vote.interceptor;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

/**
 * 国际化资源文件绑定拦截器
 * 
 * @author Anders Zhu
 * 
 */
public class VoteInterceptor extends AbstractInterceptor {

	protected Logger logger = LoggerFactory.getLogger(VoteInterceptor.class);

	private static final long serialVersionUID = -1248711503800167450L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		Map<String, Object> contextMap = actionContext.getContextMap();

		Locale locale = actionContext.getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", locale);
		ResourceBundleModel rbm = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		actionContext.getValueStack().set("res", rbm);

		if (logger.isDebugEnabled())
			logger.debug(invocation.getAction().getClass().getName() + " is binded internationalized resource file");

		HttpServletRequest request = (HttpServletRequest) contextMap.get(StrutsStatics.HTTP_REQUEST);

		if (request == null) {
			throw new RuntimeException("request is null");
		}

		String path = String.format("%s://%s:%d%s/", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
		actionContext.getValueStack().set("path", path);

		return invocation.invoke();
	}
}
