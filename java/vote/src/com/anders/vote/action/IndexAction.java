package com.anders.vote.action;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.opensymphony.xwork2.ActionContext;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

//Namespace：指定命名空间。
//ParentPackage：指定父包。
//Result：提供了Action结果的映射。（一个结果的映射）
//Results：Result注解列表
//ResultPath：指定结果页面的基路径。
//Action：指定Action的访问URL。
//Actions：Action注解列表。
//ExceptionMapping：指定异常映射。（映射一个声明异常）
//ExceptionMappings：一级声明异常的数组。
//InterceptorRef：拦截器引用。
//InterceptorRefs：拦截器引用组。

@ParentPackage("struts-default")
@Namespace("/")
@Results({ @Result(name = "success", type = "freemarker", location = "/WEB-INF/index.ftl"), @Result(name = "error", location = "/error.jsp") })
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class IndexAction extends BaseAction {

	private static final long serialVersionUID = -3013656302209804787L;

	private String user;

	private FreeMarkerViewResolver viewResolver;
	private FreeMarkerConfigurer freemarkerConfig;

	@Action(value = "index")
	public String index() {
		user = "index";
		System.out.println(viewResolver.toString());
		System.out.println(freemarkerConfig.toString());

		// TODO Anders Zhu : 考虑是否用拦截器实现
		Locale locale = ActionContext.getContext().getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message", locale);
		ResourceBundleModel rbm = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		ActionContext.getContext().put("rbm", rbm);
		return SUCCESS;
	}

	@Action(value = "add", results = { @Result(name = "success", type = "freemarker", location = "/WEB-INF/index.ftl") })
	public String add() {
		user = "add";
		return SUCCESS;
	}

	// getter and setter

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public FreeMarkerViewResolver getViewResolver() {
		return viewResolver;
	}

	public void setViewResolver(FreeMarkerViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	public FreeMarkerConfigurer getFreemarkerConfig() {
		return freemarkerConfig;
	}

	public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

}
