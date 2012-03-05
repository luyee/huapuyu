package 序列化;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Person implements Serializable {
	private static final long serialVersionUID = 3599361216299753516L;

	private String name;
	private int age;

	public Person() {
	}

	public Person(String name, int age) {
		System.out.println("person constructor");
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
