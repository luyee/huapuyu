package com.anders.ssh.rmi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试synGetRmi，这个单元测试后执行
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-remote-test.xml" })
public class SynRmiTest2 {
	@Autowired
	private AndersRmi andersRmi;

	@Test
	public void testSynGetRmi() {
		Assert.assertEquals(true, andersRmi.synGetRmi(true).contains(false));
	}
}
