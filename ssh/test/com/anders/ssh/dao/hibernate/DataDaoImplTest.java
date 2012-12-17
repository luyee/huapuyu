package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;

/**
 * 特别注意，如果继承AbstractTransactionalJUnit4SpringContextTests，数据库操作会自动回滚，如果不想回滚，加上@Rollback(false)
 * 
 * @author Anders
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" }, inheritLocations = true)
public class DataDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "hDataDao")
	private IDataDao dataDao;

	@Rollback(false)
	public void testDataAdd() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataDao.save(data);

		List<Data> dataList = dataDao.getAll();
		Assert.assertEquals(1, dataList.size());

		dataDao.delete(data);
	}

	@Test
	@Rollback(false)
	public void testDataSaveOrUpdate() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen1");
		data.setEnable(true);

		dataDao.saveOrUpdate(data);
	}

	@Test
	@Rollback(false)
	public void testDataSaveOrUpdate1() {
		Data data = new Data();
		data.setId(2L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen2");
		data.setEnable(true);
		dataDao.save(data);

		data.setName("zhuzhen3");

		dataDao.saveOrUpdate(data);
	}

	@Rollback(true)
	public void testDataMerge() {
		Data data = new Data();
		data.setId(2L);
		data.setType(Byte.MAX_VALUE);
		data.setName("zhuzhen2");
		data.setEnable(false);

		dataDao.merge(data);
	}
}
