package com.anders.ssh.dao.hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.annotation.User;

@Component("hibernateUserDao")
public class UserDao extends HibernateDao<Long, User> {
	// TODO Anders Zhu : 重构
	@SuppressWarnings("unchecked")
	public List<String> getRoleNameListByUserName(final String userName) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("enable", true));
				criteria.createCriteria("roles").setProjection(Projections.projectionList().add(Projections.property("name")));
				return criteria.list();
			}

		});
	}

	@SuppressWarnings("unchecked")
	/**
	 * 测试Hibernate的Update和Merge区别
	 */
	public void testUpdateAndMerge() {
		getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session session1 = session.getSessionFactory().openSession();
				Transaction tran1 = session1.beginTransaction();
				User user1 = new User();
				user1.setName("zhangsan");
				user1.setUserName("zhangsan");
				user1.setPassword("123");
				session1.save(user1);
				tran1.commit();
				session1.close();

				Session session2 = session.getSessionFactory().openSession();
				Transaction tran2 = session2.beginTransaction();
				User user2 = (User) session2.get(User.class, user1.getId());
				user1.setName("lisi");
				// 如果用update，报错：org.springframework.orm.hibernate3.HibernateSystemException: a different object with the same identifier value was already associated with the session: [com.anders.ssh.model.annotation.User#4]; nested exception is org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session: [com.anders.ssh.model.annotation.User#4]
				// session2.update(user1);
				// 如果用merge则ok，merge在执行更新之前会将两个标识符相同的对象进行合并，例如我将name设为lisi，merge后name的值变为lisi。
				session2.merge(user1);
				session2.delete(user2);
				tran2.commit();
				session2.close();
				return null;
			}
		});
	}

	public List<Map<String, Object>> getMap() {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("select id as id, name as name from User").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				// return session.createQuery("select new Map(id as id, name as name) from User").list();
			}

		});
	}
}
