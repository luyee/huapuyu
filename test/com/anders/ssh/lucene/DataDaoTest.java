package com.anders.ssh.lucene;

import org.compass.core.CompassTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataDaoTest {
	@Autowired
	private CompassTemplate compassTemplate;

	@Test
	public void test() {
		Person person = new Person();
		person.setId(123);
		person.setName("zhuzhen");
		compassTemplate.save(person);
		System.out.println(compassTemplate.find("zhuzhen").length());
	}
}
