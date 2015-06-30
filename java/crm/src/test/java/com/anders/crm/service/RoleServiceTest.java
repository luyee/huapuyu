package com.anders.crm.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RoleServiceTest {
	@Autowired
	private RoleService roleService;

	@Test
	public void testGetRolesByUsername() {
		Assert.assertEquals(0, roleService.getRolesByUsername("").size());
		Assert.assertEquals(0, roleService.getRolesByUsername(" ").size());
		Assert.assertEquals(0, roleService.getRolesByUsername(null).size());
		roleService.getRolesByUsername("zhuzhen");
		List<String> tttt = roleService.getRoleNames();
		for (String ttt : tttt)
			System.out.println(ttt);
		System.out.println("afasdfsa");
	}
}
