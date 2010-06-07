package others;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContextAware implements ApplicationContextAware
{
	private ApplicationContext ctx;

	// 继承了ApplicationContextAware借口后，
	// setApplicationContext方法会在MyApplicationContextAware实例化时自动调用
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException
	{
		this.ctx = ctx;
		MyApplicationContextAware aca = (MyApplicationContextAware) ctx.getBean("myApplicationContextAware");
		aca.printCtx();
	}

	public void printCtx()
	{
		System.out.println("******************This is MyApplicationContextAware******************");
	}

	public ApplicationContext getCtx()
	{
		return ctx;
	}

	public void setCtx(ApplicationContext ctx)
	{
		this.ctx = ctx;
	}
}
