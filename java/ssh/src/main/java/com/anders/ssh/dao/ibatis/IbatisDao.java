package com.anders.ssh.dao.ibatis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class IbatisDao<PK extends Serializable, T> extends SqlMapClientDaoSupport implements GenericDao<PK, T> {
	// 增加setSqlMapClientMocker方法，避免在XML文件中给DAO方法注入SqlMapClient。
	@Resource
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	private Class<T> entityClass;

	public IbatisDao() {
		entityClass = getSuperClassGenricType();
	}

	@SuppressWarnings("unchecked")
	public Class<T> getSuperClassGenricType() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getSqlMapClientTemplate().delete("delete", entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		getSqlMapClientTemplate().delete("deleteById", id);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		getSqlMapClientTemplate().insert("save", entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		getSqlMapClientTemplate().update("update", entity);
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return (T) getSqlMapClientTemplate().queryForObject("getById", id);
	}

	@Override
	public List<T> getAll() {
		return getSqlMapClientTemplate().queryForList("getAll");
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		throw new RuntimeException("没有实现");
	}
}
