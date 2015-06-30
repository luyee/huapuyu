package com.anders.ssh.aop.aspectj.xml;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.aop.CustService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-aspectj-xml-test.xml" })
public class AspectjTest {
	@Resource
	private CustService custService;

	@Test(expected = RuntimeException.class)
	public void testDelete() {
		custService.delete("zhuzhen");
	}

	@Test
	public void testSave() {
		custService.save("zhuzhen", 1);
	}

	@Test
	public void testUpdate() {
		custService.update("zhuzhen");
	}

	@Test
	public void testGet() {
		custService.get("zhuzhen", 2);
	}
}
