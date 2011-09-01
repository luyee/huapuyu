package com.anders.ssh.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
// @ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
@SpringApplicationContext( { "classpath:spring.xml", "classpath:spring-test.xml" })
public class UnitilsTest extends UnitilsJUnit4
{
	@Test
	// @DataSet("UnitilsTest.xml")
	@DataSet
	public void test1()
	{
		System.out.println("123");
	}
}
