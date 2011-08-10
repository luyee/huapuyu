package com.baidu.rigel.unique.service;

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

import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.dao.SeasonCustListPhoneDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class SeasonCustListPhoneServiceTest {
	@Autowired
	private SeasonCustListPhoneService seasonCustListPhoneService;
	@Autowired
	private SeasonCustListPhoneDao seasonCustListPhoneDao;

	@Before
	public void setUp() throws Exception {
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
	}

	@Test
	public void testSelectSeasonCustListPhoneBySeasonCustListId() {
		List<SeasonCustListPhone> list = seasonCustListPhoneService.selectSeasonCustListPhoneBySeasonCustListId(1234L);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("1234567890", list.get(0).getPhonenum());
		Assert.assertEquals(0, seasonCustListPhoneService.selectSeasonCustListPhoneBySeasonCustListId(12345L).size());
	}
}
