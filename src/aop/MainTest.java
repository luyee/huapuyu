package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest
{
	public static void main(String[] args)
	{
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		// BeanFactory bf = new XmlBeanFactory(new
		// ClassPathResource("spring.xml"));

		IProxyTarget pt = (IProxyTarget) ctx.getBean("proxyTargetFactory");
		pt.ShowMessage();
		pt.ShowName();
		pt.printMessage();

		IProxyTarget pt1 = (IProxyTarget) ctx.getBean("autoProxyTarget");
		pt1.ShowMessage();
		pt1.ShowName();
		pt1.printMessage();
	}
}
