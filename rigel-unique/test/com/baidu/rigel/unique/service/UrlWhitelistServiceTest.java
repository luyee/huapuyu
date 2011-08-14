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

import com.baidu.rigel.unique.bo.UrlWhitelist;
import com.baidu.rigel.unique.dao.UrlWhitelistDao;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class UrlWhitelistServiceTest {
	@Autowired
	private UrlWhitelistService urlWhitelistService;
	@Autowired
	private UrlWhitelistDao urlWhitelistDao;

	@Before
	public void setUp() throws Exception {
		UrlWhitelist urlWhitelist = new UrlWhitelist();
		urlWhitelist.setId(1234L);
		urlWhitelist.setDomain("mydomain");
		urlWhitelist.setCreatorId(1234L);
		urlWhitelist.setCreateTime(new Date());
		urlWhitelist.setDelFlag((byte) 0);
		urlWhitelist.setPosId(1234L);
		urlWhitelist.setUrl("www.baidu.com");
		urlWhitelist.setUpdateId(1234L);
		urlWhitelist.setUpdateTime(new Date());
		urlWhitelist.setcType((short) 0);
		urlWhitelistDao.save(urlWhitelist);
	}

	@After
	public void tearDown() throws Exception {
		urlWhitelistDao.delete(1234L);
	}

	@Test
	public void testEqualUrl() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Long[] posIds = new Long[1];
		Assert.assertTrue(urlWhitelistService.isDomainAndPosIdsExist("mydomain", posIdList.toArray(posIds)));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdsExist("mydomain123", posIdList.toArray(posIds)));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdsExist(null, posIdList.toArray(posIds)));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdsExist("mydomain123", null));
	}

	@Test
	public void testPageList() {
		List<Map<String, Object>> list = urlWhitelistService.pageList(1234L, "mydomain", (short) 0, 1234L, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("www.baidu.com", list.get(0).get(FieldConstant.URL));
		list = urlWhitelistService.pageList(12345L, "mydomain123", (short) 0, 1234L, 0, 10);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, urlWhitelistService.pageList(null, "mydomain123", (short) 0, 1234L, 0, 10).size());
		Assert.assertEquals(0, urlWhitelistService.pageList(12345L, null, (short) -1, 1L, -1, 10).size());
	}

	@Test
	public void testPageCount() {
		Assert.assertEquals(1, urlWhitelistService.pageCount(1234L, "mydomain", (short) 0, 1234L).longValue());
		Assert.assertEquals(0, urlWhitelistService.pageCount(12345L, "mydomain", (short) 0, 1234L).longValue());
		Assert.assertEquals(0, urlWhitelistService.pageCount(null, "mydomain", (short) 0, 1234L).longValue());
		Assert.assertEquals(0, urlWhitelistService.pageCount(12345L, null, (short) -1, 1L).longValue());
	}

	@Test
	public void testSelectDisUpdateIdByPosId() {
		Assert.assertEquals(1234, urlWhitelistService.selectDisUpdateIdByPosId(1234L).get(0).longValue());
		Assert.assertEquals(0, urlWhitelistService.selectDisUpdateIdByPosId(12345L).size());
		Assert.assertEquals(0, urlWhitelistService.selectDisUpdateIdByPosId(null).size());
	}

	@Test
	public void testIsDomainAndPosIdExist() {
		Assert.assertTrue(urlWhitelistService.isDomainAndPosIdExist("mydomain", 1234L));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdExist("mydomain", 12345L));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdExist(null, 12345L));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdExist("mydomain", null));
	}

	@Test
	public void testIsDomainAndPosIdsExist() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Assert.assertTrue(urlWhitelistService.isDomainAndPosIdsExist("mydomain", posIdList.toArray(new Long[posIdList.size()])));
		Assert.assertFalse(urlWhitelistService.isDomainAndPosIdsExist("mydomain123", posIdList.toArray(new Long[posIdList.size()])));
	}
}
