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
import com.baidu.rigel.unique.vo.SeasonCustListVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class SeasonCustListVoServiceTest {
	@Autowired
	private SeasonCustListDao seasonCustListDao;
	@Autowired
	private SeasonCustListPhoneDao seasonCustListPhoneDao;
	@Autowired
	private SeasonCustListVoService seasonCustListVoService;

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
		seasonCustList.setInvalidate(new Date());
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
	public void testPageList() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.add(Calendar.DATE, -1);
		endCal.add(Calendar.DATE, 1);
		List<SeasonCustListVo> list = seasonCustListVoService.pageList("zhangsan", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("mydomain", list.get(0).getSeasonCustList().getDomain());
		Assert.assertEquals("1234567890", list.get(0).getSeasonCustListPhoneList().get(0).getPhonenum());
		list = seasonCustListVoService.pageList("zhangsan123", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList, 0, 10);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testPageCount() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(1234L);
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.add(Calendar.DATE, -1);
		endCal.add(Calendar.DATE, 1);
		Assert.assertEquals(1, seasonCustListVoService.pageCount("zhangsan", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
		Assert.assertEquals(1, seasonCustListVoService.pageCount("zhangsan", "www.baidu.com", 1234L, "1234567890", null, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
		Assert.assertEquals(0, seasonCustListVoService.pageCount("zhangsan123", "www.baidu.com", 1234L, "1234567890", (short) 1, beginCal.getTime(), endCal.getTime(), posIdList).longValue());
	}

	@Test
	public void testDelete() {
		Assert.assertEquals(0, seasonCustListDao.findById(1234L).getDelFlag().shortValue());
		Assert.assertEquals(0, seasonCustListPhoneDao.findById(1234L).getDelFlag().shortValue());
		seasonCustListVoService.delete(1234L);
		Assert.assertEquals(1, seasonCustListDao.findById(1234L).getDelFlag().shortValue());
		Assert.assertEquals(1, seasonCustListPhoneDao.findById(1234L).getDelFlag().shortValue());
	}

	@Test
	public void testFindSeasonCustListVo() {
		SeasonCustListVo seasonCustListVo = seasonCustListVoService.findSeasonCustListVo(1234L);
		Assert.assertEquals("zhangsan", seasonCustListVo.getSeasonCustList().getCustName());
		Assert.assertEquals("1234567890", seasonCustListVo.getSeasonCustListPhoneList().get(0).getPhonenum());
		Assert.assertNull(seasonCustListVoService.findSeasonCustListVo(12345L));
	}
}
