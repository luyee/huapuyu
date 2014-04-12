package com.anders.ssh.ioc.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-ioc-test.xml" })
public class AnnotationTest {
	@Autowired
	private ZhuRongbao zhuRongbao;

	@Test
	public void test() {
		System.out.println(zhuRongbao.toString());
		System.out.println(zhuRongbao.getGuoLili2());
	}

}
