package aop.aspectj.annotation;

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

		IPersonService ps = (IPersonService) ctx.getBean("personService");
		ps.save("zhuzhen", 1);
		ps.update("zhuzhen");
		ps.get("zhuzhen", 2);
	}
}
