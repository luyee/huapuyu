package com.anders.ssh.dao.mybatis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Area;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class AreaDaoTest {

	@Resource(name = "mybatisAreaDao")
	private AreaDao areaDao;

	@Test
	public void test1() {
		Area area = new Area();
		area.setId(1L);
		area.setName("zhuzhen");
		area.setType((byte) 1);
		area.setEnable(true);
		// 增
		areaDao.save(area);
		// 查
		Area dataTemp = areaDao.getById(1L);
		System.out.println(dataTemp);
		area.setName("guolili");
		// 改
		areaDao.update(area);
		// 查
		dataTemp = areaDao.getById(1L);
		System.out.println(dataTemp);
		// 删
		areaDao.delete(area);
		// 查
		// List<Area> list = areaDao.getAll();
		// Assert.assertEquals(0, list.size());
	}
}
