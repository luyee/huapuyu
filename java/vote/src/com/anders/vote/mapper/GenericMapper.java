package com.anders.vote.mapper;

import java.io.Serializable;
import java.util.List;

public interface GenericMapper<PK extends Serializable, T> {

	/**
	 * 根据主键ID获取启用的BO对象
	 * 
	 * @param id
	 *            主键ID
	 * @return 启用的BO对象
	 */
	public T getById(final PK id);

	/**
	 * 获取所有启用的BO对象
	 * 
	 * @return 启用的BO对象集合
	 */
	public List<T> getAll();

	/**
	 * 根据主键ID获取BO对象（包含启用的和禁用的）
	 * 
	 * @param id
	 *            主键ID
	 * @return BO对象
	 */
	public T findById(final PK id);

	/**
	 * 根据主键ID获取BO对象数量
	 * 
	 * @param id
	 *            主键ID
	 * @return BO对象数量
	 */
	public Long findCountById(final PK id);

	/**
	 * 获取所有的BO对象（包含启用的和禁用的）
	 * 
	 * @return BO对象集合
	 */
	public List<T> findAll();

	/**
	 * 保存BO对象
	 * 
	 * @param entity
	 *            BO对象
	 */
	public void save(final T entity);

	/**
	 * 更新BO对象
	 * 
	 * @param entity
	 *            BO对象
	 */
	public void update(final T entity);

	/**
	 * 禁用BO对象
	 * 
	 * @param entity
	 *            BO对象
	 */
	public void disabled(final T entity);

	/**
	 * 删除BO对象
	 * 
	 * @param entity
	 *            BO对象
	 */
	public void delete(final T entity);
}
