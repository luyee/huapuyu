package com.anders.ethan.sharding.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" }, inheritLocations = true)
public class NestedServiceTest {

	@Resource
	private NestedService nestedService;

	@Test
	public void testInsert() {
		nestedService.insert(1L);
	}

	@Test
	public void testFindById() {
		nestedService.findById(1L);
	}
}
