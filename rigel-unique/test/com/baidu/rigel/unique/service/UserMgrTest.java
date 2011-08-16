package com.baidu.rigel.unique.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baidu.rigel.service.usercenter.service.UserMgr;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class UserMgrTest {
	@Autowired
	private UserMgr userMgr;

	@Test
	public void testGetAllPositionByIds() {
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		userMgr.getAllPositionByIds(list);
	}
}
