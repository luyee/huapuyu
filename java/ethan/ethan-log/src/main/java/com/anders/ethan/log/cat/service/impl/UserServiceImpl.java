package com.anders.ethan.log.cat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import com.anders.ethan.log.cat.entity.User;
import com.anders.ethan.log.cat.mapper.UserMapper;
import com.anders.ethan.log.cat.service.api.UserService;
import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMapper")
	private UserMapper userMapper;

	@Override
	@Transactional
	public User getById(Long id) {
		Assert.notNull(id);
		
		JedisResourcePool pool = new RoundRobinJedisPool("192.168.56.101",
				1000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
		Jedis jedis = pool.getResource();
//		jedis.set("name", "zhuzhen");

		String name = jedis.get("name");
		System.out.println(name);

		// Transaction transaction = Cat.newTransaction("Service", "sayHello");
		//
		// String result = "hello " + name + ", response form provider : "
		// + RpcContext.getContext().getLocalAddress() + ":"
		// + RpcContext.getContext().getLocalPort();
		//
		// transaction.setStatus(Transaction.SUCCESS);
		// transaction.complete();

		return userMapper.getById(id);
//		return null;
	}

	@Override
	public void save(User user) {
		Assert.notNull(user);

		userMapper.save(user);
	}
}