package com.vip.mybatis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vip.mybatis.bo.User;
import com.vip.mybatis.dao.UserDao;
import com.vip.mybatis.dao.UserMapper;
import com.vip.mybatis.util.ShardParam;

//@Component("userDao")
//public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
//
//	@Override
//	public boolean insert(User user) {
//		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);
//
//		return getSqlSession().insert("insert", shardParam) > 0;
//	}
//
//	@Override
//	public void update(User user) {
//		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);
//
//		getSqlSession().update("update", shardParam);
//	}
//}

@Component("userDao")
public class UserDaoImpl  implements UserDao {
	
	@Resource
	private UserMapper userMapper;

	@Override
	public boolean insert(User user) {
		userMapper.save(user);
		return true;
	}

	@Override
	public void update(User user) {
		userMapper.update(user);
	}
}
