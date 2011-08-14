package com.baidu.rigel.unique.service.xuanyuan;

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

import com.baidu.rigel.unique.bo.xuanyuan.CustDistribute;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.bo.xuanyuan.FollowAssign;
import com.baidu.rigel.unique.dao.xuanyuan.CustDistributeDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustUrlDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustomerDao;
import com.baidu.rigel.unique.dao.xuanyuan.FollowAssignDao;
import com.baidu.rigel.unique.service.xuanyuan.CustomerService;
import com.baidu.rigel.unique.utils.CustType;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class CustomerServiceTest {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private FollowAssignDao followAssignDao;
	@Autowired
	private CustDistributeDao custDistributeDao;
	@Autowired
	private CustUrlDao custUrlDao;

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

		FollowAssign followAssign = new FollowAssign();
		followAssign.setAssignId(1234);
		followAssign.setFollowId(1234);
		followAssign.setFollowAssignId(1234);
		followAssign.setAssignTime(new Date());
		followAssign.setCustId(1234);
		followAssignDao.save(followAssign);

		CustDistribute custDistribute = new CustDistribute();
		custDistribute.setCustId(1234);
		custDistribute.setDistributeId(1234);
		custDistribute.setDistributeTime(new Date());
		custDistributeDao.save(custDistribute);

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
		custDistributeDao.delete(1234L);
		followAssignDao.delete(1234L);
		customerDao.delete(1234L);
	}

	@Test
	public void testEqualCustFullName() {
		List<Long> list = customerService.equalCustFullName("zhangsan");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = customerService.equalCustFullName("zhangsan123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.equalCustFullName(null).size());
	}

	@Test
	public void testEqualCustBranchNameOrCustName() {
		List<Long> list = customerService.equalCustBranchNameOrCustName("zhangsan", CustType.ADVERTISING_AGENCY);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		list = customerService.equalCustBranchNameOrCustName("zhangsan123", CustType.SPECIAL_ENTERPRISE);
		Assert.assertEquals(0, list.size());
		try {
			customerService.equalCustBranchNameOrCustName("zhangsan123", CustType.PERSONAL_CUSTOMER);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(0, customerService.equalCustBranchNameOrCustName(null, CustType.PERSONAL_CUSTOMER).size());
		Assert.assertEquals(0, customerService.equalCustBranchNameOrCustName("zhangsan123", null).size());
	}

	@Test
	public void testFindCustIdPosIdByCustIds() {
		Map<Long, Long> map = customerService.findCustIdPosIdByCustIds(1234L);
		Assert.assertEquals(1, map.size());
		Assert.assertEquals(4321, map.get(1234L).longValue());
		map = customerService.findCustIdPosIdByCustIds(12345L);
		Assert.assertEquals(0, map.size());
		Assert.assertEquals(0, customerService.findCustIdPosIdByCustIds(null).size());
	}

	@Test
	public void testFindCustIdFullNameByCustIds() {
		Map<Long, String> map = customerService.findCustIdFullNameByCustIds(1234L);
		Assert.assertEquals(1, map.size());
		Assert.assertEquals("zhangsan", map.get(1234L));
		map = customerService.findCustIdFullNameByCustIds(12345L);
		Assert.assertEquals(0, map.size());
		Assert.assertEquals(0, customerService.findCustIdFullNameByCustIds(null).size());
	}

	@Test
	public void testIsCustIdsExist() {
		List<Long> list = new ArrayList<Long>();
		list.add(1234L);
		Assert.assertTrue(customerService.isCustIdsExist(list, 4321L, 1111L));
		Assert.assertFalse(customerService.isCustIdsExist(list, 54321L, 1111L));
		Assert.assertFalse(customerService.isCustIdsExist(null, 54321L, 1111L));
	}

	@Test
	public void testFindCustomerFollowDistributeByCustIdList() {
		List<Long> custIdList = new ArrayList<Long>();
		custIdList.add(1234L);
		List<Map<String, Object>> list = customerService.findCustomerFollowDistributeByCustIdList(custIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_NAME));
		custIdList = new ArrayList<Long>();
		custIdList.add(12345L);
		list = customerService.findCustomerFollowDistributeByCustIdList(custIdList);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.findCustomerFollowDistributeByCustIdList(null).size());
	}

	@Test
	public void testFindCustIdFullNamePoseIdInputTypeByCustId() {
		Map<String, Object> map = customerService.findCustIdFullNamePoseIdInputTypeByCustId(1234L);
		Assert.assertEquals(8, map.size());
		Assert.assertEquals(1234L, map.get(FieldConstant.CUSTID));
		Assert.assertEquals("zhangsan", map.get(FieldConstant.CUSTFULLNAME));
		Assert.assertEquals(4321L, map.get(FieldConstant.POSEID));
		Assert.assertEquals(0, map.get(FieldConstant.CUSTINPUTTYPE));
		map = customerService.findCustIdFullNamePoseIdInputTypeByCustId(12345L);
		Assert.assertEquals(0, map.size());
		Assert.assertEquals(0, customerService.findCustIdFullNamePoseIdInputTypeByCustId(null).size());
	}

	@Test
	public void testContainCustUrlName() {
		List<Map<String, Object>> list = customerService.containCustUrlName("www.zhangsan.com", 1);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = customerService.containCustUrlName("www.zhangsan123.com", 1);
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.containCustUrlName(null, 1).size());
		Assert.assertEquals(0, customerService.containCustUrlName("www.zhangsan123.com", -1).size());
	}

	@Test
	public void testEqualCustUrlName() {
		Customer customer = customerService.findById(1234L);
		customer.setCustStat1((byte) 5);
		customerService.saveOrUpdate(customer);
		List<Map<String, Object>> list = customerService.equalCustUrlName("www.zhangsan.com");
		Assert.assertEquals(0, list.size());
		// Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		// Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = customerService.equalCustUrlName("www.zhangsan123.com");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.equalCustUrlName(null).size());
	}

	@Test
	public void testEqualDomain() {
		Customer customer = customerService.findById(1234L);
		customer.setCustStat1((byte) 5);
		customerService.saveOrUpdate(customer);
		List<Map<String, Object>> list = customerService.equalDomain("mydomain");
		Assert.assertEquals(0, list.size());
		// Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		// Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = customerService.equalDomain("mydomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.equalDomain(null).size());
	}

	@Test
	public void testContainDomain() {
		List<Map<String, Object>> list = customerService.containDomain("mydomain");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.CUST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.CUST_FULL_NAME));
		list = customerService.containDomain("mydomain123");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(0, customerService.containDomain(null).size());
	}

	@Test
	public void testDelete() {
		Long id = null;
		customerService.delete(id);
		Assert.assertEquals("zhangsan", customerService.findById(1234L).getCustName());
		customerService.delete(1234L);
		Assert.assertNull(customerService.findById(1234L));
	}
}
