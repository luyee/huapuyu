package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("hDataDao")
public class DataDaoImpl extends HibernateDaoSupport implements IDataDao {

	@Resource
	public void setSessionFactoryMocker(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void delete(Data data) {
		getHibernateTemplate().delete(data);
	}

	@Override
	public void deleteById(Long id) {
		getHibernateTemplate().delete(this.getById(id));
	}

	@Override
	public List<Data> getAll() {
		return getHibernateTemplate().loadAll(Data.class);
	}

	@Override
	public Data getById(Long id) {
		return getHibernateTemplate().get(Data.class, id);
	}

	@Override
	public void save(Data data) {
		getHibernateTemplate().save(data);
	}

	@Override
	public void update(Data data) {
		getHibernateTemplate().update(data);
	}

}