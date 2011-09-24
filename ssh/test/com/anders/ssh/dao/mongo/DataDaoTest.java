package com.anders.ssh.dao.mongo;

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

	@Resource(name = "mongoDataDao")
	private DataDao dataDao;

	@Test
	public void test1() {
		Data data = new Data();
		data.setId(1);
		data.setName("zhuzhen");
		data.setType((byte) 1);
		data.setEnable(true);
		// 增
		dataDao.save(data);
		// 查
		Data dataTemp = dataDao.getById(1);
		System.out.println(dataTemp);
		data.setName("guolili");
		// 改
		dataDao.update(data);
		// 查
		dataTemp = dataDao.getById(1);
		System.out.println(dataTemp);
		// 删
		dataDao.delete(data);
		// 查
		List<Data> list = dataDao.getAll();
		Assert.assertEquals(0, list.size());
	}
}
