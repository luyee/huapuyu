package service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.impl.Tb_departService;

public class MainTest
{
	public static void main(String[] args)
	{
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:spring.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		// BeanFactory bf = new XmlBeanFactory(new
		// ClassPathResource("spring.xml"));

		Tb_departService service = (Tb_departService) ctx.getBean("tb_departService");
		service.getAll();
	}
}
