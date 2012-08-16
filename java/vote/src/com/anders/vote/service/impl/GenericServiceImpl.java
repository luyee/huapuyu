package com.anders.vote.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.vote.service.GenericService;

public abstract class GenericServiceImpl<PK extends Serializable, T> implements GenericService<PK, T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void saveOrUpdate(T entity) {
		getDao().save(entity);
	}

	@Override
	public T getById(PK id) {
		return getDao().getById(id);
	}

	@Override
	public List<T> getAll() {
		return getDao().getAll();
	}

	@Override
	public T findById(PK id) {
		return getDao().findById(id);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public void save(T entity) {
		getDao().save(entity);
	}

	@Override
	public void update(T entity) {
		getDao().update(entity);
	}

	@Override
	public void disabledById(PK id) {
		getDao().disabledById(id);
	}

	@Override
	public void deleteById(PK id) {
		getDao().deleteById(id);
	}

}
