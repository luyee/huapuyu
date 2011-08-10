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
import com.baidu.rigel.unique.bo.Customer;
import com.baidu.rigel.unique.bo.Phone;
import com.baidu.rigel.unique.dao.CustContactDao;
import com.baidu.rigel.unique.dao.CustomerDao;
import com.baidu.rigel.unique.dao.PhoneDao;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class PhoneServiceTest {
	@Autowired
	private PhoneDao phoneDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustContactDao custContactDao;
	@Autowired
	private PhoneService phoneService;

	@Before
	public void setUp() throws Exception {
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

		Customer customer = new Customer();
		customer.setCustId(1234L);
		customer.setPoseId(0L);
		customer.setCustType((short) 0);
		customer.setCustName("zhangsan");
		customer.setCustFullName("zhangsan");
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
	}

	@After
	public void tearDown() throws Exception {
		customerDao.delete(1234L);
		custContactDao.delete(1234L);
		phoneDao.delete(1234L);
	}

	@Test
	public void testSelectDisCustIdByFullPhone() {
		List<Long> list = phoneService.selectDisCustIdByFullPhone("123456789");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = phoneService.selectDisCustIdByFullPhone("1234567890");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSelectDisCustIdFullNameByPhoneNumAreaCode() {
		List<Map<String, Object>> list = phoneService.selectDisCustIdFullNameByPhoneNumAreaCode("4321", "987654321");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = phoneService.selectDisCustIdFullNameByPhoneNumAreaCode("54321", "987654321");
		Assert.assertEquals(0, list.size());
	}
}
