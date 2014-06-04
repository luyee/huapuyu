package com.anders.ssh.mybatis.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<PK extends Serializable, T> {
	T getById(PK id);

	void save(T entity);

	int update(T entity);

	int updateBySelective(T entity);

	void delete(T entity);

	void deleteById(PK id);

	List<T> getAll();

	void saveOrUpdate(T entity);
}