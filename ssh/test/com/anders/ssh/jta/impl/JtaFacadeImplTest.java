package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.jta.JtaFacade;
import com.anders.ssh.jta.JtaFacade1;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" }, inheritLocations = true)
public class JtaFacadeImplTest {

	@Resource(name = "jtaFacade")
	private JtaFacade jtaFacade;

	@Resource(name = "jtaFacade1")
	private JtaFacade1 jtaFacade1;

	public void testAdd1() {
		Account data = new Account();
		data.setId(1L);
		data.setName("zhuzhen");
		data.setEnable(true);
		jtaFacade.save(data);
	}

	@Test
	public void testAdd2() {
		Account data = new Account();
		data.setId(1L);
		data.setName("zhuzhen");
		data.setEnable(true);
		// jtaFacade1.save(data);
	}
}
