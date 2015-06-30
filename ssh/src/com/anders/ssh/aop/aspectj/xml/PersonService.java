package com.anders.ssh.aop.aspectj.xml;

public class PersonService implements IPersonService {
	@Override
	public void get(String name, long i) {
		System.out.println("get:" + name);
		throw new RuntimeException("错误");
	}

	@Override
	public String save(String name, int i) {
		System.out.println("save:" + name);
		return "hello world";
	}

	@Override
	public void update(String name) {
		System.out.println("update:" + name);
	}
}
