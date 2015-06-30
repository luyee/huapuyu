package aop.cglib;

public class MainTest {
	public static void main(String[] args) {
		CglibProxyFactory pf = new CglibProxyFactory();
		PersonServiceBean psPersonService = (PersonServiceBean) pf.createProxyInstance(new PersonServiceBean("ttt"));
		psPersonService.save("zhuzhen");

		PersonServiceBean psPersonService1 = (PersonServiceBean) pf.createProxyInstance(new PersonServiceBean());
		psPersonService1.save("zhuzhen");
	}
}
