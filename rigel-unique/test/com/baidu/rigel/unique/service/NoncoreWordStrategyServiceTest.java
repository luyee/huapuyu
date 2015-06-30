package com.baidu.rigel.unique.service;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baidu.rigel.unique.bo.NoncoreWordStrategy;
import com.baidu.rigel.unique.dao.NoncoreWordStrategyDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class NoncoreWordStrategyServiceTest {
	@Autowired
	private NoncoreWordStrategyService noncoreWordStrategyService;
	@Autowired
	private NoncoreWordStrategyDao noncoreWordStrategyDao;

	@Before
	public void setUp() throws Exception {
		NoncoreWordStrategy noncoreWordStrategy = new NoncoreWordStrategy();
		noncoreWordStrategy.setId(1234L);
		noncoreWordStrategy.setEnableFilter(0L);
		noncoreWordStrategy.setUpdateId(1234L);
		noncoreWordStrategy.setUpdateTime(new Date());
		noncoreWordStrategyDao.save(noncoreWordStrategy);
	}

	@After
	public void tearDown() throws Exception {
		noncoreWordStrategyDao.delete(1234L);
	}

	@Test
	public void test() {
		NoncoreWordStrategy noncoreWordStrategy = new NoncoreWordStrategy();
		noncoreWordStrategy.setId(1234567L);
		noncoreWordStrategy.setEnableFilter(1L);
		noncoreWordStrategy.setUpdateId(1234555556L);
		noncoreWordStrategy.setUpdateTime(new Date());
		noncoreWordStrategyService.saveOrUpdate(noncoreWordStrategy);
	}
}
