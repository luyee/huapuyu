package com.anders.ssh.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-common-test.xml" })
public class ParentSonTest {
	@Resource
	private ParentClass parentClass;
	@Resource
	private SonLoader sonLoader;

	@Test
	public void test() {
		parentClass.print();

		sonLoader.parentPrint();

		sonLoader.sonPrint();
	}
}
