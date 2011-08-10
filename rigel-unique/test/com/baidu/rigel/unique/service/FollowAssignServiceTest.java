package com.baidu.rigel.unique.service;

import java.util.Date;

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

import com.baidu.rigel.unique.bo.FollowAssign;
import com.baidu.rigel.unique.dao.FollowAssignDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class FollowAssignServiceTest {
	@Autowired
	private FollowAssignService followAssignService;
	@Autowired
	private FollowAssignDao followAssignDao;

	@Before
	public void setUp() throws Exception {
		FollowAssign followAssign = new FollowAssign();
		followAssign.setAssignId(1234);
		followAssign.setFollowId(1234);
		followAssign.setFollowAssignId(1234);
		followAssign.setAssignTime(new Date());
		followAssign.setCustId(1234);
		followAssignDao.save(followAssign);
	}

	@After
	public void tearDown() throws Exception {
		followAssignDao.delete(1234L);
	}

	@Test
	public void testFindCustUrlByCustId() {
		Assert.assertEquals(1234, followAssignService.getFollowerId(1234L).longValue());
		Assert.assertNull(followAssignService.getFollowerId(12345L));
	}
}
