package com.anders.ssh.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataDaoTest {
	// @Autowired
	// @Qualifier("jdbcDataDao")
	@Resource(name = "hibernateDataDao")
	private DataDao dataDao;

	public void testDataAdd() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataDao.save(data);

		data = dataDao.getById(4234243L);

		List<Data> dataList = dataDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}

	@Test
	public void testInsert5000000() {
		// 插入500万需要50分钟
		Long beginTime = new Date().getTime();
		for (int i = 1; i <= 5000000; i++) {
			Data data = new Data();
			data.setId(new Long(i));
			data.setName("zhuzhen");
			data.setType((byte) 1);
			data.setEnable(true);
			dataDao.save(data);
		}
		Long endTime = new Date().getTime();
		System.out.println("耗时：" + (endTime - beginTime) / 1000 / 60 + "分");
	}
}
