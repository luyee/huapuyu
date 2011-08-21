package com.anders.ssh.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

@Component
public class WicketApplication extends WebApplication
{
	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	// 此方法返回WebApplication的全局实例
	// public static WicketSpringApplication get()
	// {
	// return (WicketSpringApplication) WebApplication.get();
	// }

	// 此方法返回applicationContext
	// private ApplicationContext getApplicationContext()
	// {
	// return WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
	// }

	// @Override
	protected void init()
	{
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		new AnnotatedMountScanner().scanPackage("wicket").mount(this);
		getDebugSettings().setAjaxDebugModeEnabled(false);
	}

	// 此方法返回applicationContext.xml文件定义的bean
	// public UserService getUserService()
	// {
	// return (UserService) getApplicationContext().getBean("userService");
	// }
}
