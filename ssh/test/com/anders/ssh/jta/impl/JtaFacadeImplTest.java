package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;
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
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		jtaFacade.save(data);
	}

	@Test
	public void testAdd2() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		jtaFacade1.save(data);
	}
}
