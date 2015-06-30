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

	/**
	 * 根据主键ID禁用BO对象
	 * 
	 * @param id
	 *            主键ID
	 */
	void disabledById(final PK id);

	/**
	 * 根据主键ID删除BO对象
	 * 
	 * @param id
	 *            主键ID
	 */
	void deleteById(final PK id);

	/**
	 * 根据主键ID判断BO对象是否存在
	 * 
	 * @param id
	 *            主键ID
	 * @return BO对象是否存在（true：存在；false：不存在）
	 */
	boolean isExistById(final PK id);
}
