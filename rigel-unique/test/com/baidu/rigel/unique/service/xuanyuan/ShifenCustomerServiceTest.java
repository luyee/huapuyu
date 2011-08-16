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
	public void testSelectCustIdNamesLikeBySiteUrl() {
		List<Map<String, Object>> list = shifenCustomerService.selectCustIdNamesLikeBySiteUrl("siteUrl", 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.selectCustIdNamesLikeBySiteUrl("siteUrl123", 1);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdNamesLikeBySiteUrl(null, -1).size());
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
		list = shifenCustomerService.selectCustIdNamesLikeBySiteUrl("siteUrl", 1);
		Assert.assertEquals(1, list.size());
		shifenCustomerDao.delete(12345L);
	}

	@Test
	public void testSelectCustIdNamesBySiteUrl() {
		List<Map<String, Object>> list = shifenCustomerService.selectCustIdNamesBySiteUrl("siteUrl");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.selectCustIdNamesBySiteUrl("siteUrl123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdNamesBySiteUrl(null).size());
	}

	@Test
	public void testSelectCustIdByCompanyName() {
		List<Long> list = shifenCustomerService.selectCustIdByCompanyName("realCompanyName");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = shifenCustomerService.selectCustIdByCompanyName("realCompanyName123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdByCompanyName(null).size());
	}

	@Test
	public void testSelectCustIdNamesByUrlDomain() {
		List<Map<String, Object>> list = shifenCustomerService.selectCustIdNamesByUrlDomain("urlDomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.selectCustIdNamesByUrlDomain("urlDomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdNamesByUrlDomain(null).size());
	}

	@Test
	public void testSelectCustIdNamesLikeByUrlDomain() {
		List<Map<String, Object>> list = shifenCustomerService.selectCustIdNamesLikeByUrlDomain("urlDomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).get(FieldConstant.CUSTOMERD));
		Assert.assertEquals("companyName", list.get(0).get(FieldConstant.COMPANYNAME));
		Assert.assertEquals("customerName", list.get(0).get(FieldConstant.CUSTOMERNAME));
		Assert.assertEquals("realCompanyName", list.get(0).get(FieldConstant.REALCOMPANYNAME));
		list = shifenCustomerService.selectCustIdNamesLikeByUrlDomain("urlDomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectCustIdNamesLikeByUrlDomain(null).size());
	}

	@Test
	public void testSelectShifenCustomerByCustIdList() {
		List<Long> custIdList = new ArrayList<Long>();
		custIdList.add(1234L);
		List<ShifenCustomer> list = shifenCustomerService.selectShifenCustomerByCustIdList(custIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).getCustomerd().longValue());
		Assert.assertEquals("companyName", list.get(0).getCompanyname());
		Assert.assertEquals("customerName", list.get(0).getCustomername());
		Assert.assertEquals("realCompanyName", list.get(0).getRealcompanyname());
		custIdList = new ArrayList<Long>();
		custIdList.add(12345L);
		list = shifenCustomerService.selectShifenCustomerByCustIdList(custIdList);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, shifenCustomerService.selectShifenCustomerByCustIdList(null).size());
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
