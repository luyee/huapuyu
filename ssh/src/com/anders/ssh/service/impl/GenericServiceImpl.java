package com.anders.ssh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;
import com.anders.ssh.service.GenericService;

public abstract class GenericServiceImpl<PK extends Serializable, T> implements GenericService<PK, T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected abstract GenericDao<PK, T> getDao();

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getDao().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		getDao().deleteById(id);
	}

	@Override
	public List<T> getAll() {
		return getDao().getAll();
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return getDao().getById(id);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		getDao().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		getDao().saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		getDao().update(entity);
	}
}
