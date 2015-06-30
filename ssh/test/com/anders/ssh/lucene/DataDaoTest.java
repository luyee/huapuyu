package com.anders.ssh.lucene;

import org.compass.core.CompassHits;
import org.compass.core.CompassTemplate;
import org.junit.Assert;
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
		CompassHits hits = compassTemplate.find("zhuzhen");
		Assert.assertEquals(1, hits.length());

		// person = (Person) hits.data(0);

		// Assert.assertEquals(123, person.getId());
	}
}
