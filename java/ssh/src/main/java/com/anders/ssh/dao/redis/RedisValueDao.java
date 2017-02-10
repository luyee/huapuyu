package com.anders.ssh.dao.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import com.anders.ssh.bo.test.Account;

public abstract class RedisValueDao<K extends Serializable, V extends Serializable> {
	protected RedisTemplate<K, V> redisTemplate;

	@Resource
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setKeySerializer(new StringRedisSerializer());
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Account>(Account.class));
	}

	public void delete(K key) {
		Assert.notNull(key);
		redisTemplate.delete(key);
	}

	public void delete(List<K> keys) {
		Assert.notEmpty(keys);
		redisTemplate.delete(keys);
	}

	public void save(K key, V value) {
		Assert.notNull(key);
		Assert.notNull(value);
		redisTemplate.opsForValue().set(key, value);
	}

	public void save(Map<K, V> maps) {
		Assert.notEmpty(maps);
		redisTemplate.opsForValue().multiSet(maps);
	}

	public Boolean update(K key, V value) {
		Assert.notNull(key);
		Assert.notNull(value);

		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	public Boolean update(Map<K, V> maps) {
		Assert.notEmpty(maps);
		return redisTemplate.opsForValue().multiSetIfAbsent(maps);
	}

	public V get(K key) {
		Assert.notNull(key);
		return redisTemplate.opsForValue().get(key);
	}

	public List<V> get(List<K> keys) {
		Assert.notEmpty(keys);
		return redisTemplate.opsForValue().multiGet(keys);
	}
}