package com.baidu.rigel.unique.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.dao.BlacklistPhoneDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class BlacklistPhoneServiceTest {
	@Autowired
	private BlacklistPhoneService blacklistPhoneService;
	@Autowired
	private BlacklistPhoneDao blacklistPhoneDao;

	@Before
	public void setUp() throws Exception {
		BlacklistPhone blacklistPhone = new BlacklistPhone();
		blacklistPhone.setId(1234L);
		blacklistPhone.setBlacklistId(1234L);
		blacklistPhone.setDelFlag((short) 0);
		blacklistPhone.setPhonenum("1234567890");
		blacklistPhoneDao.save(blacklistPhone);
	}

	@After
	public void tearDown() throws Exception {
		blacklistPhoneDao.delete(1234L);
	}

	@Test
	public void testEqualPhoneNum() {
		List<Long> list = blacklistPhoneService.equalPhoneNum("1234567890");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = blacklistPhoneService.equalPhoneNum("1234567890123");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testFindById() {
		BlacklistPhone blacklistPhone = blacklistPhoneService.findById(1234L);
		Assert.assertEquals("1234567890", blacklistPhone.getPhonenum());
		Assert.assertEquals(1234, blacklistPhone.getBlacklistId().longValue());
		Assert.assertNull(blacklistPhoneService.findById(12345L));
	}

	@Test
	public void testSelectBlacklistPhoneByBlacklistId() {
		List<BlacklistPhone> list = blacklistPhoneService.selectBlacklistPhoneByBlacklistId(1234L);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("1234567890", list.get(0).getPhonenum());
		list = blacklistPhoneService.selectBlacklistPhoneByBlacklistId(12345L);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSaveOrUpdate() {
		BlacklistPhone blacklistPhone = new BlacklistPhone();
		blacklistPhone.setBlacklistId(1234L);
		blacklistPhone.setDelFlag((short) 0);
		blacklistPhone.setPhonenum("1234567890");
		blacklistPhoneService.saveOrUpdate(blacklistPhone);
		Assert.assertNotNull(blacklistPhone.getId());
		blacklistPhoneDao.delete(blacklistPhone.getId());
	}
}
