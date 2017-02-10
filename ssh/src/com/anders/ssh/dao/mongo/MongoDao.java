package com.anders.ssh.dao.mongo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import com.anders.ssh.dao.GenericDao;

public abstract class MongoDao<PK extends Serializable, T> implements GenericDao<PK, T> {
	protected MongoTemplate mongoTemplate;

	@Resource
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	private Class<T> entityClass;

	public MongoDao() {
		entityClass = getSuperClassGenricType();
	}

	public Class<T> getSuperClassGenricType() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		mongoTemplate.remove(entity);
	}

	@Override
	public void deleteById(PK id) {
		Assert.notNull(id);
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), entityClass);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		// mongoTemplate.save(entity);
		mongoTemplate.insert(entity);
	}

	@Override
	public void update(T entity) {
		Assert.notNull(entity);
		// mongoTemplate.updateFirst(new Query(Criteria.where("id").is(company.getId())), Update.update("name", company.getName()), Company.class);
		throw new RuntimeException("没有实现");
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(id);
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), entityClass);
	}

	@Override
	public List<T> getAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		throw new RuntimeException("没有实现");
	}
}