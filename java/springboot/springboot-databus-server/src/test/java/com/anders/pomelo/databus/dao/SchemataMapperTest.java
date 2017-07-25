package com.anders.pomelo.databus.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.pomelo.databus.dao.bo.Schemata;
import com.anders.pomelo.databus.dao.mapper.SchemataMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao-test.xml" })
public class SchemataMapperTest {

	@Autowired
	private SchemataMapper schemataMapper;

	@Test
	public void test() {
		List<Schemata> schemataList = schemataMapper.selectAll();
		for (Schemata schemata : schemataList) {
			System.out.println(schemata.getDefaultCharacterSetName());
			System.out.println(schemata.getSchemaName());
			System.out.println("*************************************");
		}
	}

}
