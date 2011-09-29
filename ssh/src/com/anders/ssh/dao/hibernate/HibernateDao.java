package com.anders.ssh.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.anders.ssh.dao.Dao;

public abstract class HibernateDao<PK extends Serializable, T> extends HibernateDaoSupport implements Dao<PK, T> {
	// 增加setSessionFactoryMocker方法，避免在XML文件中给DAO方法注入SessionFactory。
	@Resource
	public void setSessionFactoryMocker(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entityClass;

	public HibernateDao() {
		entityClass = getSuperClassGenricType();
	}

	@SuppressWarnings("unchecked")
	public Class<T> getSuperClassGenricType() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		getHibernateTemplate().delete(this.getById(id));
	}

	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public T getById(PK id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Object... values) {
		return getHibernateTemplate().find(hql, values);
	}
}
