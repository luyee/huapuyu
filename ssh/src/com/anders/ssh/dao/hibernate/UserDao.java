package com.anders.ssh.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.annotation.User;

@Component("hibernateUserDao")
public class UserDao extends HibernateDao<Long, User> {
	// TODO Anders Zhu : 重构
	public List<String> getRoleNameListByUserName(final String userName) {
		return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("enable", true));
				criteria.createCriteria("roles").setProjection(Projections.projectionList().add(Projections.property("name")));
				return criteria.list();
			}

		});
	}
}
