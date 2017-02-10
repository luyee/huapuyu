package com.anders.ssh.dao.mybatis;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;

public abstract class MybatisDao<PK extends Serializable, T> implements GenericDao<PK, T> {
	protected abstract GenericMapper<PK, T> getMapper();

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getMapper().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		getMapper().deleteById(id);
	}

	@Override
	public List<T> getAll() {
		return getMapper().getAll();
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return getMapper().getById(id);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		getMapper().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getMapper().saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		getMapper().update(entity);
	}
}
