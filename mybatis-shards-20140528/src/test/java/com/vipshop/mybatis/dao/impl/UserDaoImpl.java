package com.vipshop.mybatis.dao.impl;

import org.springframework.stereotype.Component;

import com.vipshop.mybatis.bo.User;
import com.vipshop.mybatis.common.ShardParam;
import com.vipshop.mybatis.dao.UserDao;
import com.vipshop.mybatis.support.SqlSessionDaoSupport;

@Component("userDao")
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	@Override
	public boolean insert(User user) {
		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);

		return getSqlSession().insert("insert", shardParam) > 0;
	}

	@Override
	public void update(User user) {
		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);

		getSqlSession().update("update", shardParam);
	}
}
