package com.anders.crm.service.impl;

import java.util.List;

import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.LuUser;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.LuUserDao;
import com.anders.crm.service.LuUserService;

//@Service("userService")
@Service
@Transactional
public class LuUserServiceImpl extends GenericServiceImpl<Long, LuUser> implements LuUserService {

	@Autowired
	private LuUserDao luUserDao;

	@Override
	public GenericDao<Long, LuUser> getDao() {
		return luUserDao;
	}

	@Override
	public void addLucene() {
		LuUser user = new LuUser();
		user.setUsername("zhuzhen6");
		user.setPassword("123");
		user.setName("zhuzhen6");
		user.setEmail("zhuzhen6");
		luUserDao.save(user);
	}

	@Override
	public void getLucene() {
		FullTextSession fullTextSession = Search.getFullTextSession(luUserDao.getSession());

		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(LuUser.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("name").matching("zhuzhen6").createQuery();

		org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(query, LuUser.class);

		List<LuUser> users = fullTextQuery.list();
		for (LuUser u : users) {
			System.out.println(u.getName());
		}
	}

}
