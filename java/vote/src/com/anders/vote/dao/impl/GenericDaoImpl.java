package com.anders.vote.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.anders.vote.bo.BaseBO;
import com.anders.vote.dao.GenericDao;

public abstract class GenericDaoImpl<PK extends Serializable, T extends BaseBO<PK>> implements GenericDao<PK, T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public T getById(PK id) {
		Assert.notNull(id, "id is null");
		return getMapper().getById(id);
	}

	@Override
	public List<T> getAll() {
		return getMapper().findAll();
	}

	@Override
	public T findById(PK id) {
		Assert.notNull(id, "id is null");
		return getMapper().findById(id);
	}

	@Override
	public Long findCountById(PK id) {
		Assert.notNull(id, "id is null");
		return getMapper().findCountById(id);
	}

	@Override
	public List<T> findAll() {
		return getMapper().findAll();
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity, "entity is null");
		getMapper().save(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity, "entity is null");
		getMapper().update(entity);
	}

	// TODO Anders Zhu : 看下hibernate是如何实现的
	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity, "entity is null");
		if (entity.getId() != null && isExistById(entity.getId())) {
			getMapper().update(entity);
		}
		else {
			getMapper().save(entity);
		}
	}

	@Override
	public void disabledById(PK id) {
		Assert.notNull(id, "id is null");
		T entity = getMapper().findById(id);
		Assert.notNull(entity, "entity is null");
		getMapper().disabled(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id, "id is null");
		T entity = getMapper().findById(id);
		Assert.notNull(entity, "entity is null");
		getMapper().delete(entity);
	}

	@Override
	public void disabled(T entity) {
		Assert.notNull(entity, "entity is null");
		getMapper().disabled(entity);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity, "entity is null");
		getMapper().delete(entity);
	}

	@Override
	public boolean isExistById(PK id) {
		Assert.notNull(id, "id is null");
		return getMapper().findCountById(id) == 1 ? true : false;
	}
}
