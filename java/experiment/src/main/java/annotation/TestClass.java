package annotation;

@Annotation4Class(myClass = "this is a class")
public class TestClass {
	@Annotation4Field(isTrue = false, myField = "this is a field")
	private String myField = "hello world";

	@Annotation4Method(myMethod = "this is a method")
	public void myMethod() {
		System.out.println(myField);
	}
}
