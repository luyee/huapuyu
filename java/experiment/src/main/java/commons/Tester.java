package commons;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

public class Tester {
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String s1 = null;
		String s2 = " ";
		String s3 = StringUtils.EMPTY;

		if (StringUtils.isBlank(s1))
			System.out.println("null");

		if (StringUtils.isBlank(s2))
			System.out.println("space");

		if (StringUtils.isBlank(s3))
			System.out.println("empty");

		System.out.println("------------------------");

		if (StringUtils.isEmpty(s1))
			System.out.println("null");

		if (StringUtils.isEmpty(s2))
			System.out.println("space");

		if (StringUtils.isEmpty(s3))
			System.out.println("empty");

		System.out.println("------------------------");

		List<String> list = null;
		if (CollectionUtils.isEmpty(list))
			System.out.println("null");

		list = new ArrayList<String>();
		if (CollectionUtils.isEmpty(list))
			System.out.println("empty");

		System.out.println("------------------------");
		User u1 = new User();
		u1.setId(1L);
		u1.setName("zhuzhen");
		u1.setDesc("hello");

		User u2 = new User();
		// u2.setId(2L);
		// u2.setName("guolili");
		// u2.setDesc("hello world");

		User u3 = new User();

		BeanUtils.copyProperties(u1, u2);
		// 经过测试，对于下面User类，由于是放在本文件中，spring的BeanUtils.copyProperties可以正确执行，但是apache的BeanUtils.copyProperties无法执行
		org.apache.commons.beanutils.BeanUtils.copyProperties(u3, u1);
		System.out.println(u1);
		System.out.println(u2);
		System.out.println(u3);

		System.out.println("********************shuffle*************************");
		List<String> names = new ArrayList<String>();
		names.add("aa");
		names.add("bb");
		names.add("cc");
		names.add("dd");
		for (String name : names)
			System.out.println(name);

		Collections.shuffle(names);
		for (String name : names)
			System.out.println(name);

		System.out.println("********************1*************************");
		for (int i = 0; i < names.size(); i++) {
			System.out.println("[" + i + "]:::" + names.get(i));
		}

		Collections.shuffle(names);
		System.out.println("********************2*************************");
		for (int i = 0; i < names.size(); i++) {
			System.out.println("[" + i + "]:::" + names.get(i));
		}
	}
}

// class User {
// private Long id;
// private String name;
// private String desc;
//
// public Long getId() {
// return id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getName() {
// return name;
// }
//
// public void setName(String name) {
// this.name = name;
// }
//
// public String getDesc() {
// return desc;
// }
//
// public void setDesc(String desc) {
// this.desc = desc;
// }
//
// @Override
// public String toString() {
// return ReflectionToStringBuilder.toString(this);
// }
// }
