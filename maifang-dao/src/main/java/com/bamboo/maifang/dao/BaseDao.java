package com.bamboo.maifang.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDao<T, ID extends Serializable> extends
		HibernateDaoSupport{

	@Resource
	public void setSessionFactory0(SessionFactory sessionFactory){
	  super.setSessionFactory(sessionFactory);
	}

	
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass =  getEntityClass();
	}

	public Class<T> getEntityClass()
	{
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[0];
	}
	/**
	 * 持久化对象.
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	@SuppressWarnings("hiding")
	public <T> T saveNotUpdate(T entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteById(ID id) {
		getHibernateTemplate().delete(get(id));
	}
	
	@SuppressWarnings( { "hiding", "unchecked" })
	public <T> T get(ID id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	public List<T>  getAll() {
		return getHibernateTemplate().find("from " + entityClass.getSimpleName());
	}
}
