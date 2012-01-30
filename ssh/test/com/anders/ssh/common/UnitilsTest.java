package com.anders.ssh.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.dao.hibernate.DataDao;
import com.anders.ssh.model.xml.Data;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext( { "classpath:spring.xml", "classpath:spring-test.xml" })
public class UnitilsTest extends UnitilsJUnit4 {
	@SpringBean("hibernateDataDao")
	private DataDao dataDao;

	@Test
	// @DataSet("UnitilsTest.xml")
	@DataSet
	@Transactional(TransactionMode.COMMIT)
	public void test1() {
		Data data = new Data();
		data.setId(123L);
		data.setName("test");
		data.setEnable(true);
		data.setType((byte) 123);
		dataDao.save(data);
		// throw new RuntimeException();
		// System.out.println("123");
	}
}
