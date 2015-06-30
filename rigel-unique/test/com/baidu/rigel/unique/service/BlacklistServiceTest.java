package com.baidu.rigel.unique.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.baidu.rigel.unique.dao.BlacklistDao;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class BlacklistServiceTest {
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private BlacklistDao blacklistDao;

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
	}

	@After
	public void tearDown() throws Exception {
		blacklistDao.delete(1234L);
	}

	@Test
	public void testEqualCompanyName() {
		List<Long> list = blacklistService.equalCompanyName("zhangsan");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = blacklistService.equalCompanyName("zhangsan123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, blacklistService.equalCompanyName(null).size());
	}

	@Test
	public void testContainCompanyName() {
		List<Map<String, Object>> list = blacklistService.containCompanyName("zhangsan");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.BLACKLIST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.COMPANY_NAME));
		list = blacklistService.containCompanyName("zhangsan123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, blacklistService.containCompanyName(null).size());
	}

	@Test
	public void testEqualUrl() {
		List<Long> list = blacklistService.equalUrl("www.baidu.com.cn");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).intValue());
		list = blacklistService.equalUrl("www.baidu123.com.cn");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, blacklistService.equalUrl(null).size());
	}

	@Test
	public void testFindById() {
		Blacklist blacklist = blacklistService.findById(1234L);
		Assert.assertEquals(1234, blacklist.getBlacklistId().longValue());
		Assert.assertEquals("zhangsan", blacklist.getCompanyName());
		Assert.assertEquals("12345678", blacklist.getCompanyPhone());
		Assert.assertNull(blacklistService.findById(12345L));
	}

	@Test
	public void testSelectDisCreatorIdList() {
		List<Long> list = blacklistService.selectDisCreatorIdList();
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
	}

	@Test
	public void testPageList() {
		List<Short> srcList = new ArrayList<Short>();
		srcList.add((short) 12);
		List<Blacklist> list = blacklistService.pageList("zhangsan", "www.baidu.com.cn", 1234L, srcList, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("zhangsan", list.get(0).getCompanyName());
		list = blacklistService.pageList("zhangsan123", null, null, null, 0, 10);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, blacklistService.pageList(null, "www.baidu123.com.cn", null, null, -1, 10).size());
	}

	@Test
	public void testPageCount() {
		List<Short> srcList = new ArrayList<Short>();
		srcList.add((short) 12);
		Assert.assertEquals(1, blacklistService.pageCount("zhangsan", "www.baidu.com.cn", 1234L, srcList).longValue());
		Assert.assertEquals(0, blacklistService.pageCount("zhangsan123", null, null, null).longValue());
		Assert.assertEquals(0, blacklistService.pageCount(null, "www.baidu123.com.cn", null, null).longValue());
	}

	@Test
	public void testSaveOrUpdate() {
		Blacklist blacklist = new Blacklist();
		blacklist.setCompanyName("zhangsan");
		blacklist.setCompanyPhone("12345678");
		blacklist.setCreatorId(1234L);
		blacklist.setCreateTime(new Date());
		blacklist.setUpdateId(1234L);
		blacklist.setUpdateTime(new Date());
		blacklist.setSrc((short) 12);
		blacklist.setDelFlag((short) 0);
		blacklist.setUrl("www.baidu.com.cn");
		blacklistService.saveOrUpdate(blacklist);
		Assert.assertNotNull(blacklist.getBlacklistId());
		blacklistDao.delete(blacklist.getBlacklistId());
	}
}
