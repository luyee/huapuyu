package com.anders.ssh.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;

public abstract class HibernateDao<PK extends Serializable, T> extends HibernateDaoSupport implements GenericDao<PK, T> {
	// 增加setSessionFactoryMocker方法，避免在XML文件中给DAO方法注入SessionFactory。
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entityClass;

	public HibernateDao() {
		entityClass = getSuperClassGenricType();
	}

	public Class<T> getSuperClassGenricType() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		T entity = getById(id);
		if (entity != null)
			getHibernateTemplate().delete(entity);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().update(entity);
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getHibernateTemplate().saveOrUpdate(entity);
		// getHibernateTemplate().merge(entity);
	}

	public List<T> find(final String hql, final Object... values) {
		return getHibernateTemplate().find(hql, values);
	}
}
