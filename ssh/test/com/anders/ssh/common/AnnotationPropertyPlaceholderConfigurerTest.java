package com.anders.ssh.common;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class AnnotationPropertyPlaceholderConfigurerTest
{
	@Resource
	private ConfigPojo configPojo;

	@Test
	public void test1()
	{
		Assert.assertEquals(Boolean.FALSE.toString(), configPojo.getValue());
	}
}
