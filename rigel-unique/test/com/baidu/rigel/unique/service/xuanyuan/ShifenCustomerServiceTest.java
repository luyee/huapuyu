package com.baidu.rigel.unique.service.xuanyuan;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;
import com.baidu.rigel.unique.dao.xuanyuan.ShifenCustomerDao;
import com.baidu.rigel.unique.service.xuanyuan.ShifenCustomerService;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class ShifenCustomerServiceTest {
	@Autowired
	private ShifenCustomerService shifenCustomerService;
	@Autowired
	private ShifenCustomerDao shifenCustomerDao;

	@Before
	public void setUp() throws Exception {
		ShifenCustomer shifenCustomer = new ShifenCustomer();
		shifenCustomer.setCompanyname("companyName");
		shifenCustomer.setCustomerd(1234);
		shifenCustomer.setCustomername("customerName");
		shifenCustomer.setDomain("domain");
		shifenCustomer.setUrldomain("urlDomain");
		shifenCustomer.setRealcompanyname("realCompanyName");
		shifenCustomer.setSiteurl("siteUrl");
		shifenCustomer.setStatus((byte) 2);
		shifenCustomerDao.save(shifenCustomer);
	}

	@After
	public void tearDown() throws Exception {
		shifenCustomerDao.delete(1234L);
	}

	@Test
	public void testContainSiteUrl() {
		List<Map<String, Object>> list = shifenCustomerService.containSiteUrl("siteUrl", 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.containSiteUrl("siteUrl123", 1);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.containSiteUrl(null, -1).size());
		ShifenCustomer shifenCustomer = new ShifenCustomer();
		shifenCustomer.setCompanyname("companyName");
		shifenCustomer.setCustomerd(12345);
		shifenCustomer.setCustomername("customerName");
		shifenCustomer.setDomain("domain");
		shifenCustomer.setUrldomain("urlDomain");
		shifenCustomer.setRealcompanyname("realCompanyName");
		shifenCustomer.setSiteurl("siteUrl");
		shifenCustomer.setStatus((byte) 1);
		shifenCustomer.setAccountmoney(new BigDecimal(10));
		shifenCustomerDao.save(shifenCustomer);
		list = shifenCustomerService.containSiteUrl("siteUrl", 1);
		Assert.assertEquals(1, list.size());
		shifenCustomerDao.delete(12345L);
	}

	@Test
	public void testEqualSiteUrl() {
		List<Map<String, Object>> list = shifenCustomerService.equalSiteUrl("siteUrl");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.equalSiteUrl("siteUrl123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.equalSiteUrl(null).size());
	}

	@Test
	public void testEqualCompanyName() {
		List<Long> list = shifenCustomerService.equalCompanyName("realCompanyName");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = shifenCustomerService.equalCompanyName("realCompanyName123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.equalCompanyName(null).size());
	}

	@Test
	public void testEqualUrlDomain() {
		List<Map<String, Object>> list = shifenCustomerService.equalUrlDomain("urlDomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.equalUrlDomain("urlDomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.equalUrlDomain(null).size());
	}

	@Test
	public void testContainUrlDomain() {
		List<Map<String, Object>> list = shifenCustomerService.containUrlDomain("urlDomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.containUrlDomain("urlDomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.containUrlDomain(null).size());
	}

	@Test
	public void testGetShifenCustomerByCustIdList() {
		List<Long> custIdList = new ArrayList<Long>();
		custIdList.add(1234L);
		List<ShifenCustomer> list = shifenCustomerService.getShifenCustomerByCustIdList(custIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustomerd().longValue());
		Assert.assertEquals("companyName", list.get(0).getCompanyname());
		Assert.assertEquals("customerName", list.get(0).getCustomername());
		Assert.assertEquals("realCompanyName", list.get(0).getRealcompanyname());
		custIdList = new ArrayList<Long>();
		custIdList.add(12345L);
		list = shifenCustomerService.getShifenCustomerByCustIdList(custIdList);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.getShifenCustomerByCustIdList(null).size());
	}

	@Test
	public void testSelectCustIdByCompanyNameSiteUrl() {
		List<Long> list = shifenCustomerService.selectCustIdByCompanyNameSiteUrl("companyName", "siteUrl");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdByCompanyNameSiteUrl("companyName123", "siteUrl").size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdByCompanyNameSiteUrl(null, "siteUrl").size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdByCompanyNameSiteUrl("companyName123", null).size());
	}
}
