package com.anders.ssh.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataServiceTest {
	@Autowired
	private DataService dataService;

	@Before
	public void before() {
		dataService.deleteById(1L);
	}

	@After
	public void after() {
		dataService.deleteById(1L);
	}

	@Test
	public void testDataAdd() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataService.save(data);

		List<Data> dataList = dataService.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}

}
