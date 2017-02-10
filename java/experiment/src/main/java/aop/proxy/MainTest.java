package aop.proxy;

public class MainTest {
	public static void main(String[] args) {
		ProxyFactory pf = new ProxyFactory();
		PersonService psPersonService = (PersonService) pf.createProxyInstance(new PersonServiceBean("tttt"));
		psPersonService.save("zhuzhen");

		PersonService psPersonService1 = (PersonService) pf.createProxyInstance(new PersonServiceBean());
		psPersonService1.save("zhuzhen");
	}
}
