package com.anders.ssh.rmi;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class AndersRmiTest {
	@Autowired
	private AndersRmi andersRmi;

	@Test
	public void testGetUserInfo() {
		Map<Long, String> map = andersRmi.getUserInfo();
		Assert.assertEquals(2, map.size());
		Assert.assertEquals("zhuzhen", map.get(1L));
		Assert.assertEquals("guolili", map.get(2L));
	}
}
