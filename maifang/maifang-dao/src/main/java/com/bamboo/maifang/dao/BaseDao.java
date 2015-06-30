package com.bamboo.maifang.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bamboo.maifang.beans.Condition;

public interface BaseDao<T, ID extends Serializable>{
	
	public void setSessionFactory0(SessionFactory sessionFactory);

	public Class<T> getEntityClass();
	
	/**
	 *增 
	 */
	public <T> T saveOrUpdate(T entity);

	public <T> T saveNotUpdate(T entity);
	
	/**
	 *删
	 */
	public void delete(T entity);

	public void deleteById(ID id);
	
	/**
	 *改
	 */
	
	/**
	 *查
	 */
	public <T> T get(ID id);

	public List<T>  getAll();
	
	public List<T>  getByCriteria(Condition condition);
	/**
	 * 工具方法
	 */
}
