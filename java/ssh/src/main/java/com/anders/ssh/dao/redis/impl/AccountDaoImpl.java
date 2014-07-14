package com.anders.ssh.dao.redis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.redis.AccountDao;

@Component("redisAccountDao")
public class AccountDaoImpl implements AccountDao {
	protected RedisTemplate<String, Account> redisTemplate;

	@Resource
	public void setRedisTemplate(RedisTemplate<String, Account> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setKeySerializer(new StringRedisSerializer());
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Account>(Account.class));
	}

	@Override
	public void delete(Account entity) {
	}

	@Override
	public void deleteById(Long id, Account entity) {
	}

	@Override
	public List<Account> getAll() {
		return null;
	}

	@Override
	public Account getById(Long id) {
		return null;
	}

	@Override
	public void save(Long id, Account entity) {
		// // string type
		// redisTemplate.opsForValue().set("stringType", "zhuzhen");
		// // hash type
		// redisTemplate.opsForHash().put("hashType", "name", "zhuzhen");
		// redisTemplate.opsForHash().put("hashType", "age", "23");
		// redisTemplate.opsForHash().put("hashType", "money", "10000");
		// // list type
		// redisTemplate.opsForList().leftPush("listType", "zhuzhen");
		// redisTemplate.opsForList().leftPush("listType", "guolili");
		// redisTemplate.opsForList().leftPush("listType", "zhulili");
		// redisTemplate.opsForList().rightPush("listType", "zhuzhenzhen");
		// // set type
		// redisTemplate.opsForSet().add("setType", "zhuzhen");
		// redisTemplate.opsForSet().add("setType", "guolili");
		// redisTemplate.opsForSet().add("setType", "zhulili");
		// // sorted set type
		// redisTemplate.opsForZSet().add("sortedSetType", "zhuzhen", 2);
		// redisTemplate.opsForZSet().add("sortedSetType", "guolili", 3);
		// redisTemplate.opsForZSet().add("sortedSetType", "zhulili", 1);

		// string type
		redisTemplate.opsForValue().set("stringType1", entity);
		// hash type
		redisTemplate.opsForHash().put("hashType1", "name", entity);
		redisTemplate.opsForHash().put("hashType1", "age", entity);
		redisTemplate.opsForHash().put("hashType1", "money", entity);
		// list type
		redisTemplate.opsForList().leftPush("listType1", entity);
		redisTemplate.opsForList().leftPush("listType1", entity);
		redisTemplate.opsForList().leftPush("listType1", entity);
		redisTemplate.opsForList().rightPush("listType1", entity);
		// set type
		redisTemplate.opsForSet().add("setType1", entity);
		redisTemplate.opsForSet().add("setType1", entity);
		redisTemplate.opsForSet().add("setType1", entity);
		// sorted set type
		redisTemplate.opsForZSet().add("sortedSetType1", entity, 2);
		redisTemplate.opsForZSet().add("sortedSetType1", entity, 3);
		redisTemplate.opsForZSet().add("sortedSetType1", entity, 1);

		System.out.println(redisTemplate.opsForValue().get("stringType1"));

		System.out.println(((Account) redisTemplate.opsForHash().get("hashType1", "name")).getName());
		System.out.println(((Account) redisTemplate.opsForHash().get("hashType1", "age")).getId());
		System.out.println(((Account) redisTemplate.opsForHash().get("hashType1", "money")).getEnable());
	}

	@Override
	public void saveOrUpdate(Account entity) {
	}

	@Override
	public void update(Account entity) {
	}
}
