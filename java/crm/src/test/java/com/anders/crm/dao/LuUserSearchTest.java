package com.anders.crm.dao;

import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.crm.service.LuUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LuUserSearchTest {
	@Autowired
	private LuUserDao luUserDao;
	@Autowired
	private LuUserService luUserService;

	@Test
	public void test1() {
		// luUserService.addLucene();
		luUserService.getLucene();
	}

	@Rollback(false)
	public void testGet() throws InterruptedException {
		FullTextSession fullTextSession = Search.getFullTextSession(luUserDao.getSession());
		fullTextSession.createIndexer().startAndWait();
	}

}
