package com.anders.ssh.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test
{
	@Autowired
	private TestObject testObject;

	public TestObject getTestObject()
	{
		return testObject;
	}

	public void setTestObject(TestObject testObject)
	{
		this.testObject = testObject;
	}

	@PostConstruct
	public void test123()
	{
		System.out.println("hello world");
	}
}
