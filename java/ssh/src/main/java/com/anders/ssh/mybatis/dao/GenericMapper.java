package com.anders.ssh.mybatis.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericMapper<PK extends Serializable, T, C> {
	int countByCriteria(C criteria);

	int deleteByCriteria(C criteria);

	int deleteByPrimaryKey(PK id);

	int insert(T entity);

	int insertSelective(T entity);

	List<T> selectByCriteria(C criteria);

	T selectByPrimaryKey(PK id);

	int updateByCriteriaSelective(T entity, C criteria);

	int updateByCriteria(T entity, C criteria);

	int updateByPrimaryKeySelective(T entity);

	int updateByPrimaryKey(T entity);
}
