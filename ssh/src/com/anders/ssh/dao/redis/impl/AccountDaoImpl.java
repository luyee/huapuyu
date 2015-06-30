package com.anders.ssh.dao.redis.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.redis.AccountDao;

@Component("redisAccountDao")
public class AccountDaoImpl implements AccountDao {
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	@Resource
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void delete(Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id, Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Account> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Long id, Account entity) {
		// TODO Auto-generated method stub
		// redisTemplate.opsForValue().set("test1", 1L);

		redisTemplate.opsForHash().put("user1", "name", "zhuzhen");
		redisTemplate.opsForHash().put("user1", "age", "23");
		redisTemplate.opsForHash().put("user1", "money", "10000");

		System.out.println(redisTemplate.opsForHash().get("user1", "name"));
	}

	@Override
	public void saveOrUpdate(Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Account entity) {
		// TODO Auto-generated method stub

	}
}
