package ioc;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class MainTest
{
	public static void main(String[] args)
	{
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring.xml"));

		Pojo pojo = (Pojo) ctx.getBean("pojo");
		System.out.println(pojo);

		pojo = (Pojo) bf.getBean("pojo");
		System.out.println(pojo);

		SetterInjection si = (SetterInjection) ctx.getBean("setterInjection");
		System.out.println(si);

		ConstructorInjection ci = (ConstructorInjection) ctx.getBean("constructorInjection");
		System.out.println(ci);

		FactoryInjection fi = (FactoryInjection) ctx.getBean("factoryInjection");
		System.out.println(fi);
	}
}
