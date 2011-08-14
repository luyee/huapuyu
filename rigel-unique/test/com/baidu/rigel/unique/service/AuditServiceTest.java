package com.baidu.rigel.unique.service;

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

import com.baidu.rigel.unique.bo.xuanyuan.CustContact;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;
import com.baidu.rigel.unique.dao.xuanyuan.CustContactDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustUrlDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustomerDao;
import com.baidu.rigel.unique.dao.xuanyuan.PhoneDao;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class AuditServiceTest {
	@Autowired
	private AuditService auditService;
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
	public void testListMatchCustUrl() {
		List<Map<String, Object>> list = auditService.listMatchCustUrl("www.zhangsan.com");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = auditService.listMatchCustUrl("www.zhangsan123.com");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testListPreMatchCustUrl() {
		List<Map<String, Object>> list = auditService.listPreMatchCustUrl("www.zhangsan.com", 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = auditService.listPreMatchCustUrl("www.zhangsan123.com", 1);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testListMatchDomain() {
		List<Map<String, Object>> list = auditService.listMatchDomain("mydomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = auditService.listMatchDomain("mydomain123");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testListPreMatchDomain() {
		List<Map<String, Object>> list = auditService.listPreMatchDomain("mydomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = auditService.listPreMatchDomain("mydomain123");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSelectDisCustIdByFullPhone() {
		List<Long> list = auditService.listMatchPhoneContact("123456789");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = auditService.listMatchPhoneContact("1234567890");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, auditService.listMatchPhoneContact(null).size());
	}

	@Test
	public void testSelectDisCustIdFullNameByPhoneNumAreaCode() {
		List<Map<String, Object>> list = auditService.listMatchPhoneContactMap("4321", "987654321");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = auditService.listMatchPhoneContactMap("54321", "987654321");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, auditService.listMatchPhoneContactMap("54321", null).size());
		Assert.assertEquals(0, auditService.listMatchPhoneContactMap(null, "9876543210").size());
	}

}
