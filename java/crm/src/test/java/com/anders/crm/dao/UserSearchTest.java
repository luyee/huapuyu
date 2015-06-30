package com.anders.crm.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserSearchTest {
	@Autowired
	private UserDao userDao;

	@Test
	@Rollback(false)
	public void test1() throws InterruptedException {
		// for (int i = 0; i < 10000; i++) {
		// User user = new User();
		// user.setUsername("anders.zhu2");
		// user.setPassword("123");
		// user.setName("anders.zhu2");
		// user.setEmail("mail2");
		// user.setAddUser(user);
		// userDao.save(user);
		// }

		System.out.println(new Date().getTime() + 1000);

		Thread.sleep(1000);

		System.out.println(new Date().getTime());

		// FullTextSession fullTextSession = Search.getFullTextSession(userDao.getSessionFactory().openSession());
		//
		// QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
		// org.apache.lucene.search.Query query = qb.keyword().onFields("name").matching("anders.zhu1").createQuery();
		//
		// org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, User.class);
		//
		// List<User> result = hibQuery.list();
		// for (User user1 : result) {
		// System.out.println(user1.getName());
		// }

	}
}
