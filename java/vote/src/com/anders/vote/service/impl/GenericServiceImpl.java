package com.anders.vote.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.service.GenericService;

public abstract class GenericServiceImpl<PK extends Serializable, T> implements GenericService<PK, T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// TODO Anders Zhu：其实这个方法真的没必要存在。想办法优化掉
	@Override
	public GenericMapper<PK, T> getMapper() {
		return getDao().getMapper();
	}

	@Override
	public void saveOrUpdate(T entity) {
		getDao().save(entity);
	}

	@Override
	public T getById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}

		return getDao().getById(id);
	}

	@Override
	public List<T> getAll() {
		return getDao().getAll();
	}

	@Override
	public T findById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		return getDao().findById(id);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public void save(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("entity is null");
		}
		getDao().save(entity);
	}

	@Override
	public void update(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("entity is null");
		}
		getDao().update(entity);
	}

	@Override
	public void disabledById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		getDao().disabledById(id);
	}

	@Override
	public void deleteById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		getDao().deleteById(id);
	}

	@Override
	public boolean isExistById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		return getDao().isExistById(id);
	}

	@Override
	public Long findCountById(PK id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		return getDao().findCountById(id);
	}

	@Override
	public void disabled(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("entity is null");
		}
		getDao().disabled(entity);
	}

	@Override
	public void delete(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("entity is null");
		}
		getDao().delete(entity);
	}

}
