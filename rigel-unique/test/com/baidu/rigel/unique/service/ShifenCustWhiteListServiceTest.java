package com.baidu.rigel.unique.service;

import java.util.ArrayList;
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

import com.baidu.rigel.unique.bo.ShifenCustWhiteList;
import com.baidu.rigel.unique.dao.ShifenCustWhiteListDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class ShifenCustWhiteListServiceTest {
	@Autowired
	private ShifenCustWhiteListService shifenCustWhiteListService;
	@Autowired
	private ShifenCustWhiteListDao shifenCustWhiteListDao;

	@Before
	public void setUp() throws Exception {
		ShifenCustWhiteList shifenCustWhiteList = new ShifenCustWhiteList();
		shifenCustWhiteList.setDelFlag((short) 0);
		shifenCustWhiteList.setId(1234);
		shifenCustWhiteList.setCustId(1234L);
		shifenCustWhiteList.setCustName("zhangsan");
		shifenCustWhiteList.setUrl("www.baidu.com.cn");
		shifenCustWhiteList.setUserId(1234L);
		shifenCustWhiteList.setPosId(4321L);
		shifenCustWhiteList.setUpdateId(1234L);
		shifenCustWhiteList.setCreateId(1234L);
		shifenCustWhiteList.setCreateTime(new Date());
		shifenCustWhiteList.setUpdateTime(new Date());
		shifenCustWhiteList.setDelFlag((short) 0);
		shifenCustWhiteListDao.save(shifenCustWhiteList);
	}

	@After
	public void tearDown() throws Exception {
		shifenCustWhiteListDao.delete(1234L);
	}

	@Test
	public void testEqualUrl() {
		ShifenCustWhiteList shifenCustWhiteList = shifenCustWhiteListService.equalUrl("www.baidu.com.cn");
		Assert.assertEquals(1234, shifenCustWhiteList.getCustId().intValue());
		Assert.assertEquals("zhangsan", shifenCustWhiteList.getCustName());
		Assert.assertEquals(1234, shifenCustWhiteList.getCreateId().intValue());
		Assert.assertEquals(1234, shifenCustWhiteList.getUpdateId().intValue());
		Assert.assertNull(shifenCustWhiteListService.equalUrl("www.baidu123.com.cn"));
		Assert.assertNull(shifenCustWhiteListService.equalUrl(null));
	}

	@Test
	public void testSelectDisUserIdByPosIdList() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(4321L);
		List<Long> list = shifenCustWhiteListService.selectDisUserIdByPosIdList(posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = shifenCustWhiteListService.selectDisUserIdByPosIdList(posIdList);
		Assert.assertEquals(0, list.size());
		list = shifenCustWhiteListService.selectDisUserIdByPosIdList(null);
		Assert.assertEquals(1, list.size());
		list = shifenCustWhiteListService.selectDisUserIdByPosIdList(new ArrayList<Long>(0));
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testSelectDisCreateIdByPosIdList() {
		List<Long> posIdList = new ArrayList<Long>();
		posIdList.add(4321L);
		List<Long> list = shifenCustWhiteListService.selectDisCreateIdByPosIdList(posIdList);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
		posIdList = new ArrayList<Long>();
		posIdList.add(12345L);
		list = shifenCustWhiteListService.selectDisCreateIdByPosIdList(posIdList);
		Assert.assertEquals(0, list.size());
		list = shifenCustWhiteListService.selectDisCreateIdByPosIdList(null);
		Assert.assertEquals(1, list.size());
		list = shifenCustWhiteListService.selectDisCreateIdByPosIdList(new ArrayList<Long>(0));
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testPageList() {
		List<Long> posIdList = new ArrayList<Long>(0);
		posIdList.add(4321L);
		List<ShifenCustWhiteList> list = shifenCustWhiteListService.pageList("zhangsan", "www.baidu.com.cn", 1234L, 1234L, posIdList, 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("www.baidu.com.cn", list.get(0).getUrl());
		Assert.assertEquals(0, shifenCustWhiteListService.pageList("zhangsan123", "www.baidu.com.cn", 1234L, 1234L, posIdList, 0, 10).size());
		Assert.assertEquals(1, shifenCustWhiteListService.pageList(null, null, 1234L, 1234L, posIdList, -1, 10).size());
	}

	@Test
	public void testPageCount() {
		List<Long> posIdList = new ArrayList<Long>(0);
		posIdList.add(4321L);
		Assert.assertEquals(1, shifenCustWhiteListService.pageCount("zhangsan", "www.baidu.com.cn", 1234L, 1234L, posIdList).longValue());
		Assert.assertEquals(0, shifenCustWhiteListService.pageCount("zhangsan123", "www.baidu.com.cn", 1234L, 1234L, posIdList).longValue());
		Assert.assertEquals(1, shifenCustWhiteListService.pageCount(null, null, 1234L, 1234L, posIdList).longValue());
	}

	@Test
	public void testDeleteShifenCustWhiteList() {
		Assert.assertEquals(0, shifenCustWhiteListService.findById(1234L).getDelFlag().shortValue());
		shifenCustWhiteListService.deleteShifenCustWhiteList(1234L);
		Assert.assertEquals(1, shifenCustWhiteListService.findById(1234L).getDelFlag().shortValue());
	}
}
