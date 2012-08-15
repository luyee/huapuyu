package com.anders.vote.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.anders.vote.bo.BaseBO;
import com.anders.vote.dao.GenericDao;

public abstract class GenericDaoImpl<PK extends Serializable, T extends BaseBO<PK>> implements GenericDao<PK, T> {

	@Override
	public T getById(PK id) {
		return getMapper().getById(id);
	}

	// TODO Anders Zhu : 看下hibernate是如何实现的
	@Override
	public void saveOrUpdate(T entity) {
		if (getMapper().findById(entity.getId()) == null) {
			getMapper().save(entity);
		}
		else {
			getMapper().update(entity);
		}
	}

	@Override
	public List<T> getAll() {
		return getMapper().findAll();
	}

	@Override
	public T findById(PK id) {
		return getMapper().findById(id);
	}

	@Override
	public List<T> findAll() {
		return getMapper().findAll();
	}

	@Override
	public void save(T entity) {
		getMapper().save(entity);
	}

	@Override
	public void update(T entity) {
		getMapper().update(entity);
	}

	@Override
	public void disabledById(PK id) {
		getMapper().disabledById(id);
	}

	@Override
	public void deleteById(PK id) {
		getMapper().deleteById(id);
	}
}
