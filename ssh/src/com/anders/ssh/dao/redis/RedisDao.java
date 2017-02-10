package com.anders.ssh.dao.redis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public abstract class RedisDao<PK extends Serializable, T> {
	protected RedisTemplate<PK, T> redisTemplate;

	@Resource
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private Class<T> entityClass;

	public RedisDao() {
		entityClass = getSuperClassGenricType();
	}

	public Class<T> getSuperClassGenricType() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	public void delete(T entity) {
		Assert.notNull(entity);
	}

	public void deleteById(PK id, T entity) {
		Assert.notNull(id);
		Assert.notNull(entity);
		redisTemplate.opsForValue().get(id);
	}

	public void save(PK id, T entity) {
		Assert.notNull(id);
		Assert.notNull(entity);
		redisTemplate.opsForValue().set(id, entity);
		// redisTemplate.opsForSet().add(arg0, arg1)
	}

	public void update(T entity) {
		Assert.notNull(entity);
		throw new RuntimeException("没有实现");
	}

	public T getById(PK id) {
		Assert.notNull(id);
		throw new RuntimeException("没有实现");
	}

	public List<T> getAll() {
		throw new RuntimeException("没有实现");
	}

	public void saveOrUpdate(T entity) {
		Assert.notNull(entity);
		throw new RuntimeException("没有实现");
	}
}