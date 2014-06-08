package com.vipshop.mybatis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vip.mybatis.util.ShardParam;
import com.vipshop.mybatis.bo.User;
import com.vipshop.mybatis.dao.UserDao;
import com.vipshop.mybatis.dao.UserMapper;

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
