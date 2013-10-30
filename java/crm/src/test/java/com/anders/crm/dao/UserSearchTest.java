package com.anders.crm.dao;

import java.awt.print.Book;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserSearchTest {
	@Autowired
	private UserDao userDao;

	@Test
	@Transactional()
	@Rollback(false)
	public void test1() {
		
		for (int i = 0; i < 10000; i++) {
		User user = new User();
		user.setUsername(String.valueOf(i));
		user.setPassword("123");
		user.setName(String.valueOf(i));
		user.setEmail(String.valueOf(i));
		userDao.save(user);
		}

//		FullTextSession fullTextSession = Search.getFullTextSession(userDao.getSessionFactory().openSession());
//
//		QueryBuilder qb = fullTextSession.getSearchFactory()
//				.buildQueryBuilder().forEntity(User.class).get();
//		org.apache.lucene.search.Query query = qb.keyword().onFields("name")
//				.matching("Zhu Zhen").createQuery();
//
//		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
//				query, User.class);
//
//		List<User> result = hibQuery.list();
//		for (User user1 : result) {
//			System.out.println(user1.getName());
//		}

	}

}
