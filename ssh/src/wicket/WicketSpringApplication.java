package wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketSpringApplication extends WebApplication
{
	@Override
	public Class<? extends Page> getHomePage()
	{
		return ListPage.class;
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
		super.getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		addComponentInstantiationListener(new SpringComponentInjector(this));
	}

	// 此方法返回applicationContext.xml文件定义的bean
	// public UserService getUserService()
	// {
	// return (UserService) getApplicationContext().getBean("userService");
	// }
}
