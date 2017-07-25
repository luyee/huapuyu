package com.anders.pomelo.databus.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.pomelo.databus.dao.bo.Columns;
import com.anders.pomelo.databus.dao.mapper.ColumnsMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao-test.xml" })
public class ColumnsMapperTest {

	@Autowired
	private ColumnsMapper columnsMapper;

	@Test
	public void test() {
		List<Columns> columnsList = columnsMapper.selectByTableSchema("eif_test");
		for (Columns columns : columnsList) {
			System.out.println(columns.getCharacterSetName());
			System.out.println(columns.getColumnKey());
			System.out.println(columns.getColumnName());
			System.out.println(columns.getColumnType());
			System.out.println(columns.getDataType());
			System.out.println(columns.getTableName());
			System.out.println(columns.getDatetimePrecision());
			System.out.println(columns.getOrdinalPosition());
			System.out.println("*************************************");
		}
	}

}
