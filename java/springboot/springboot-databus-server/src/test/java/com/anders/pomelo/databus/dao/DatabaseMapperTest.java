package com.anders.pomelo.databus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.pomelo.databus.dao.mapper.DatabaseMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao-test.xml" })
public class DatabaseMapperTest {

	@Autowired
	private DatabaseMapper databaseMapper;

	@Test
	public void test() {
		System.out.println(databaseMapper.selectCharacterSetServer());
	}

}
