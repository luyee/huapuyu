package com.baidu.rigel.unique.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.baidu.rigel.unique.bo.CustContact;
import com.baidu.rigel.unique.bo.CustUrl;
import com.baidu.rigel.unique.bo.Customer;
import com.baidu.rigel.unique.bo.Phone;
import com.baidu.rigel.unique.dao.CustContactDao;
import com.baidu.rigel.unique.dao.CustUrlDao;
import com.baidu.rigel.unique.dao.CustomerDao;
import com.baidu.rigel.unique.dao.PhoneDao;
import com.baidu.rigel.unique.utils.FieldConstant;
import com.baidu.rigel.unique.utils.SourceType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class TestServiceTest {
	@Autowired
	private com.baidu.rigel.unique.service.Test testService;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private PhoneDao phoneDao;
	@Autowired
	private CustUrlDao custUrlDao;
	@Autowired
	private CustContactDao custContactDao;

	@Before
	public void setUp() throws Exception {
		Customer customer = new Customer();
		customer.setCustId(1234L);
		customer.setPoseId(4321L);
		customer.setCustType((short) 3);
		customer.setCustName("zhangsan");
		customer.setCustFullName("zhangsan");
		customer.setCustBranchName("zhangsan");
		customer.setCustTrade1(0);
		customer.setCustTrade2(0);
		customer.setCustPriority((byte) 0);
		customer.setCustStat1((byte) 0);
		customer.setCustStat2((byte) 0);
		customer.setBlackFlag((byte) 0);
		customer.setAddUser(0L);
		customer.setAddTime(new Date());
		customer.setCustInputType((byte) 0);
		customer.setCustHaveWebSite((byte) 0);
		customerDao.save(customer);

		CustUrl custUrl = new CustUrl();
		custUrl.setCustUrlId(1234);
		custUrl.setCustId(1234);
		custUrl.setCustUrlName("www.zhangsan.com");
		custUrl.setAddUser(1234);
		custUrl.setAddTime(new Date());
		custUrl.setDomain("mydomain");
		custUrlDao.save(custUrl);

		Phone phone = new Phone();
		phone.setPhoneId(1234);
		phone.setFullPhone("123456789");
		phone.setPhNum("987654321");
		phone.setPhType((byte) 0);
		phone.setCustId(1234);
		phone.setContactOrRecipid(1234);
		phone.setFlag((byte) 0);
		phone.setAddUser(0);
		phone.setAddTime(new Date());
		phone.setDelFlag((byte) 0);
		phone.setDisabled((byte) 0);
		phone.setPhAreaCode("4321");
		phoneDao.save(phone);

		CustContact custContact = new CustContact();
		custContact.setContactId(1234);
		custContact.setCustId(1234);
		custContact.setContactName("zhangsan");
		custContact.setAddUser(0);
		custContact.setAddTime(new Date());
		custContact.setDelFlag((byte) 0);
		custContact.setDisabled((byte) 0);
		custContactDao.save(custContact);
	}

	@After
	public void tearDown() throws Exception {
		custContactDao.delete(1234L);
		phoneDao.delete(1234L);
		custUrlDao.delete(1234L);
		customerDao.delete(1234L);
	}

	@Test
	public void testContainContactPhone() {
		List<Map<String, Object>> list = testService.containContactPhone("", "", 3);
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(1L, list.get(0).get(FieldConstant.CUSTID));
		Assert.assertEquals("realcompanyname", list.get(0).get(FieldConstant.CUSTNAME));
		Assert.assertEquals(SourceType.CUST_SOURCE_SHIFEN.toString(), list.get(0).get(FieldConstant.CUSTSOURCE));
		Assert.assertEquals(2L, list.get(1).get(FieldConstant.CUSTID));
		Assert.assertEquals("companyname", list.get(1).get(FieldConstant.CUSTNAME));
		Assert.assertEquals(3L, list.get(2).get(FieldConstant.CUSTID));
		Assert.assertEquals("customername", list.get(2).get(FieldConstant.CUSTNAME));
		Assert.assertEquals(0, testService.containContactPhone(null, null, 3).size());
		Assert.assertEquals(0, testService.containContactPhone("", "", 0).size());
		Assert.assertEquals(0, testService.containContactPhone("", "", -1).size());
	}

	@Test
	public void testEqualContactPhone() {
		List<Map<String, Object>> list = testService.equalContactPhone("4321", "987654321", 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUSTID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUSTNAME));
		Assert.assertEquals(SourceType.CUST_SOURCE_SALE.toString(), list.get(0).get(FieldConstant.CUSTSOURCE));
		list = testService.equalContactPhone("54321", "987654321", 1);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, testService.equalContactPhone(null, null, 3).size());
		Assert.assertEquals(0, testService.equalContactPhone("", "", 0).size());
		Assert.assertEquals(0, testService.equalContactPhone("", "", -1).size());
	}
}
