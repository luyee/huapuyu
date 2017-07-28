package com.anders.pomelo.databus.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.pomelo.databus.dao.bo.KeyColumnUsage;
import com.anders.pomelo.databus.dao.mapper.KeyColumnUsageMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao-test.xml" })
public class KeyColumnUsageMapperTest {

	@Autowired
	private KeyColumnUsageMapper keyColumnUsageMapper;

	@Test
	public void test() {
		List<KeyColumnUsage> keyColumnUsageList = keyColumnUsageMapper.selectByTableSchema("anders");
		for (KeyColumnUsage keyColumnUsage : keyColumnUsageList) {
			System.out.println(keyColumnUsage.getColumnName());
			System.out.println(keyColumnUsage.getTableName());
			System.out.println(keyColumnUsage.getOrdinalPosition());
			System.out.println("*************************************");
		}
	}

}
