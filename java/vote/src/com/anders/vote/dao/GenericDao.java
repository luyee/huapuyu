package com.anders.vote.dao;

import java.io.Serializable;

import com.anders.vote.mapper.GenericMapper;

public interface GenericDao<PK extends Serializable, T> extends GenericMapper<PK, T> {
	GenericMapper<PK, T> getMapper();

	/**
	 * 保存或更新BO对象
	 * 
	 * @param entity
	 *            BO对象
	 */
	void saveOrUpdate(final T entity);
}
