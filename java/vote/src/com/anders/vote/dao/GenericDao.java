package com.anders.vote.dao;

import java.io.Serializable;

import com.anders.vote.mapper.GenericMapper;

public interface GenericDao<PK extends Serializable, T> extends GenericMapper<PK, T> {
	public void saveOrUpdate(final T entity);
}
