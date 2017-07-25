package com.anders.pomelo.databus.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.pomelo.databus.dao.bo.Tables;
import com.anders.pomelo.databus.dao.mapper.TablesMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao-test.xml" })
public class TablesMapperTest {

	@Autowired
	private TablesMapper tablesMapper;

	@Test
	public void test() {
		List<Tables> tablesList = tablesMapper.selectByTableSchema("eif_test");
		for (Tables tables : tablesList) {
			System.out.println(tables.getCharacterSetName());
			System.out.println(tables.getTableName());
			System.out.println("*************************************");
		}
	}

}
