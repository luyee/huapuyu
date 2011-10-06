package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.anders.ssh.model.xml.Data;

@ContextConfiguration(locations = { "classpath:test/testng/dao/hibernate/spring.xml" })
public class DataDaoTestNG extends AbstractTestNGSpringContextTests {
	@Resource(name = "hibernateDataDao")
	private DataDao dataDao;

	@Test
	public void testUserAdd() {
		Data data = new Data();
		data.setId(1);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataDao.save(data);

		List<Data> dataList = dataDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}
}
