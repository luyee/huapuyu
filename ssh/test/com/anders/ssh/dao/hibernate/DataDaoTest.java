package com.anders.ssh.dao.hibernate;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.model.Data;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test/junit/dao/hibernate/spring.xml" })
public class DataDaoTest
{
	// @Resource(name = "hibernateDataDao")
	// @Resource
	@Autowired
	// @Qualifier("hibernateDataDao")
	private DataDao dataDao;

	@Test
	public void testDataAdd()
	{
		Data data = new Data();
		data.setId(1);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		dataDao.save(data);

		List<Data> dataList = dataDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}
}
