package com.baidu.rigel.unique.service;

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

import com.baidu.rigel.unique.bo.CustUrl;
import com.baidu.rigel.unique.dao.CustUrlDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class CustUrlServiceTest {
	@Autowired
	private CustUrlDao custUrlDao;
	@Autowired
	private CustUrlService custUrlService;

	@Before
	public void setUp() throws Exception {
		CustUrl custUrl = new CustUrl();
		custUrl.setCustUrlId(1234);
		custUrl.setCustId(1234);
		custUrl.setCustUrlName("www.zhangsan.com");
		custUrl.setAddUser(1234);
		custUrl.setAddTime(new Date());
		custUrl.setDomain("mydomain");
		custUrlDao.save(custUrl);
	}

	@After
	public void tearDown() throws Exception {
		custUrlDao.delete(1234L);
	}

	@Test
	public void testFindCustUrlByCustId() {
		List<CustUrl> list = custUrlService.findCustUrlByCustId(1234L);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("mydomain", list.get(0).getDomain());
		Assert.assertEquals("www.zhangsan.com", list.get(0).getCustUrlName());
		list = custUrlDao.selectCustUrlByCustId(12345L);
		Assert.assertEquals(0, list.size());
	}
}
