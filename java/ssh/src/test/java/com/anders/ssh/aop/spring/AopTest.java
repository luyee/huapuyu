package com.anders.ssh.aop.spring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.aop.CustService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-aop-test.xml" })
public class AopTest {
	@Resource
	private CustService custService;
	@Resource(name = "proxyCustService")
	private CustService proxyCustService;

	@Test(expected = RuntimeException.class)
	public void testDelete() {
		proxyCustService.delete("zhuzhen");
	}

	@Test
	public void testSave() {
		custService.save("zhuzhen", 1);
	}

	@Test
	public void testUpdate() {
		proxyCustService.update("zhuzhen");
	}

	@Test
	public void testGet() {
		custService.get("zhuzhen", 2);
	}

}
