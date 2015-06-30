package com.baidu.rigel.unique.dao;

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

import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.dao.BlacklistDao;
import com.baidu.rigel.unique.utils.FieldConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class BlacklistDaoTest {
	@Autowired
	private BlacklistDao blacklistDao;

	@Before
	public void setUp() throws Exception {
		Blacklist blacklist = new Blacklist();
		blacklist.setBlacklistId(1234L);
		blacklist.setCompanyName("zhangsan");
		blacklist.setCompanyPhone("12345678");
		blacklist.setCreatorId(1234L);
		blacklist.setCreateTime(new Date());
		blacklist.setUpdateId(1234L);
		blacklist.setUpdateTime(new Date());
		blacklist.setSrc((short) 0);
		blacklist.setDelFlag((short) 0);
		blacklistDao.save(blacklist);
	}

	@After
	public void tearDown() throws Exception {
		blacklistDao.delete(1234L);
	}

	@Test
	public void testSelectBlacklistIdByCompanyName() {
		List<Long> list = blacklistDao.selectBlacklistIdByCompanyName("zhangsan");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
	}

	@Test
	public void testSelectBlacklistIdCompanyNameLikeByCompanyName() {
		List<Map<String, Object>> list = blacklistDao.selectBlacklistIdCompanyNameLikeByCompanyName("zhangsan");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234L, list.get(0).get(FieldConstant.BLACKLIST_ID));
		Assert.assertEquals("zhangsan", list.get(0).get(FieldConstant.COMPANY_NAME));
	}
}
