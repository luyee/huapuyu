package com.baidu.rigel.unique.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.dao.SeasonCustListDao;
import com.baidu.rigel.unique.dao.SeasonCustListPhoneDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class SeasonCustListServiceTest {
	@Autowired
	private SeasonCustListDao seasonCustListDao;
	@Autowired
	private SeasonCustListPhoneDao seasonCustListPhoneDao;
	@Autowired
	private SeasonCustListService seasonCustListService;

	@Before
	public void setUp() throws Exception {
		SeasonCustList seasonCustList = new SeasonCustList();
		seasonCustList.setId(1234L);
		seasonCustList.setCreateId(1234L);
		seasonCustList.setCreateTime(new Date());
		seasonCustList.setCustId(1234L);
		seasonCustList.setCustName("zhangsan");
		seasonCustList.setUrl("www.baidu.com");
		seasonCustList.setPosId(1234L);
		seasonCustList.setUpdateId(1234L);
		seasonCustList.setUpdateTime(new Date());
		seasonCustList.setDelFlag((short) 0);
		seasonCustList.setDomain("mydomain");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		seasonCustList.setInvalidate(calendar.getTime());
		seasonCustListDao.save(seasonCustList);

		SeasonCustListPhone seasonCustListPhone = new SeasonCustListPhone();
		seasonCustListPhone.setDelFlag((short) 0);
		seasonCustListPhone.setId(1234L);
		seasonCustListPhone.setPhonenum("1234567890");
		seasonCustListPhone.setSeasonCustlistId(1234L);
		seasonCustListPhoneDao.save(seasonCustListPhone);
	}

	@After
	public void tearDown() throws Exception {
		seasonCustListPhoneDao.delete(1234L);
		seasonCustListDao.delete(1234L);
	}

	@Test
	public void testEqualUrl() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		List<SeasonCustList> list = seasonCustListService.equalUrl("www.baidu.com", posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustId().longValue());
		Assert.assertEquals("zhangsan", list.get(0).getCustName());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = seasonCustListService.equalUrl("www.baidu123.com", posIdList);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testQuerySeasonCustDataByCoreWord() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		List<SeasonCustList> list = seasonCustListService.querySeasonCustDataByCoreWord("zhangsan", posIdList, 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustId().longValue());
		Assert.assertEquals("zhangsan", list.get(0).getCustName());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = seasonCustListService.querySeasonCustDataByCoreWord("zhangsan123", posIdList, 1);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testQuerySeasonCustDataByDomain() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		List<SeasonCustList> list = seasonCustListService.querySeasonCustDataByDomain("mydomain", posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustId().longValue());
		Assert.assertEquals("zhangsan", list.get(0).getCustName());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = seasonCustListService.querySeasonCustDataByDomain("mydomain123", posIdList);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testQuerySeasonCustDataByPhone() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		List<SeasonCustList> list = seasonCustListService.querySeasonCustDataByPhone("1234567890", posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustId().longValue());
		Assert.assertEquals("zhangsan", list.get(0).getCustName());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = seasonCustListService.querySeasonCustDataByPhone("1234567890123", posIdList);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSelectDisCreateIdByPosIdList() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		List<Long> list = seasonCustListService.selectDisCreateIdByPosIdList(posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = seasonCustListService.selectDisCreateIdByPosIdList(posIdList);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testPageList() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.add(Calendar.DATE, -1);
		endCal.add(Calendar.DATE, 2);
		List<SeasonCustList> list = seasonCustListService.pageList("zhangsan", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("mydomain", list.get(0).getDomain());
		list = seasonCustListService.pageList("zhangsan123", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList, 0, 10);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testPageCount() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.add(Calendar.DATE, -1);
		endCal.add(Calendar.DATE, 2);
		Assert.assertEquals(1, seasonCustListService.pageCount("zhangsan", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
		Assert.assertEquals(1, seasonCustListService.pageCount("zhangsan", "www.baidu.com", 1234L, "1234567890", null, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
		Assert.assertEquals(0, seasonCustListService.pageCount("zhangsan123", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
	}
}
