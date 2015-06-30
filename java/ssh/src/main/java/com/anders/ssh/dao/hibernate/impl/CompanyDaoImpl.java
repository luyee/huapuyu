package com.anders.ssh.dao.hibernate.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.hibernate.CompanyDao;
import com.anders.ssh.dao.hibernate.HibernateDao;

@Component("hibernateCompanyDao")
public class CompanyDaoImpl extends HibernateDao<Long, Company> implements CompanyDao {

	// @Resource
	// public void setSessionFactoryMocker(SessionFactory sessionFactory) {
	// super.setSessionFactory(sessionFactory);
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAccountNameByName(final String name) {
		return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Company.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("enable", true));
				criteria.createCriteria("accounts").setProjection(Projections.projectionList().add(Projections.property("name")));
				return criteria.list();
			}
		});
	}

}
