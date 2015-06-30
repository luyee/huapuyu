package com.anders.ssh.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;

public abstract class JpaDao<PK extends Serializable, T> extends JpaDaoSupport implements GenericDao<PK, T> {
	// 增加setEntityManagerFactoryMocker方法，避免在XML文件中给DAO方法注入EntityManagerFactory。
	@Resource
	public void setSuperEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		super.setEntityManagerFactory(entityManagerFactory);
	}

	private Class<T> entityClass;

	public JpaDao() {
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
		getJpaTemplate().remove(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		getJpaTemplate().remove(getJpaTemplate().getReference(entityClass, id));
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		getJpaTemplate().persist(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		getJpaTemplate().merge(entity);
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return getJpaTemplate().find(entityClass, id);
	}

	@Override
	public List<T> getAll() {
		String hql = String.format("select %1$s from %2$s %1$s", entityClass.getSimpleName().toLowerCase(), entityClass.getSimpleName());
		return getJpaTemplate().find(hql);
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		throw new RuntimeException("没有实现");
	}
}
