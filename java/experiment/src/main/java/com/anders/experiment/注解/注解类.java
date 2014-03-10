package com.anders.experiment.注解;

@Annotation4Class(myClass = "this is a class")
public class 注解类 {
	@Annotation4Field(isTrue = false, myField = "this is a field")
	private String myField = "hello world";

	@Annotation4Method(myMethod = "this is a method")
	public void myMethod() {
		System.out.println(myField);
	}
}
