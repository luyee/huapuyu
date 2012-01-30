package com.anders.ssh.dao.jdbc;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.model.xml.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataDaoTest {
	@Resource(name = "jdbcDataDao")
	private DataDao dataDao;

	@Test
	public void testDataAdd() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataDao.save(data);

		List<Data> dataList = dataDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}
}
