package com.baidu.rigel.unique.service;

import java.util.ArrayList;
import java.util.Date;
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

import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.dao.BlacklistDao;
import com.baidu.rigel.unique.dao.BlacklistPhoneDao;
import com.baidu.rigel.unique.vo.BlacklistVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class BlacklistVoServiceTest {
	@Autowired
	private BlacklistVoService blacklistVoService;
	@Autowired
	private BlacklistDao blacklistDao;
	@Autowired
	private BlacklistPhoneDao blacklistPhoneDao;

	@Before
	public void setUp() throws Exception {
		Blacklist blacklist = new Blacklist();
		blacklist.setBlacklistId(1234L);
		blacklist.setCompanyName("zhangsan");
		blacklist.setCompanyPhone("12345678");
		blacklist.setCreatorId(1234L);
		blacklist.setCreateTime(new Date());
		blacklist.setUpdateId(1234L);
		blacklist.setUpdateTime(new Date());
		blacklist.setSrc((short) 12);
		blacklist.setDelFlag((short) 0);
		blacklist.setUrl("www.baidu.com.cn");
		blacklistDao.save(blacklist);

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
		blacklistDao.delete(1234L);
	}

	@Test
	public void testPageList() {
		List<Short> srcList = new ArrayList<Short>();
		srcList.add((short) 12);
		List<BlacklistVo> list = blacklistVoService.pageList("zhangsan", "www.baidu.com.cn", 1234L, srcList, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("zhangsan", list.get(0).getBlacklist().getCompanyName());
		Assert.assertEquals("1234567890", list.get(0).getBlacklistPhoneList().get(0).getPhonenum());
		list = blacklistVoService.pageList("zhangsan123", null, null, null, 0, 10);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testPageCount() {
		List<Short> srcList = new ArrayList<Short>();
		srcList.add((short) 12);
		Assert.assertEquals(1, blacklistVoService.pageCount("zhangsan", "www.baidu.com.cn", 1234L, srcList).longValue());
		Assert.assertEquals(0, blacklistVoService.pageCount("zhangsan123", null, null, null).longValue());
	}

	@Test
	public void testDelete() {
		Assert.assertEquals(0, blacklistDao.findById(1234L).getDelFlag().shortValue());
		Assert.assertEquals(0, blacklistPhoneDao.findById(1234L).getDelFlag().shortValue());
		blacklistVoService.delete(1234L);
		Assert.assertEquals(1, blacklistDao.findById(1234L).getDelFlag().shortValue());
		Assert.assertEquals(1, blacklistPhoneDao.findById(1234L).getDelFlag().shortValue());
	}

	@Test
	public void testFindBlacklistVo() {
		BlacklistVo blacklistVo = blacklistVoService.findBlacklistVo(1234L);
		Assert.assertEquals("zhangsan", blacklistVo.getBlacklist().getCompanyName());
		Assert.assertEquals("1234567890", blacklistVo.getBlacklistPhoneList().get(0).getPhonenum());
		Assert.assertNull(blacklistVoService.findBlacklistVo(12345L));
	}

}
