package com.vipshop.mybatis.dao.impl;

import com.vipshop.mybatis.bo.User;
import com.vipshop.mybatis.common.ShardParam;
import com.vipshop.mybatis.dao.UserDao;
import com.vipshop.mybatis.spring.SqlSessionDaoSupport;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	@Override
	public boolean insert(User user) {
		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);

		return getSqlSession().insert("user.insert", shardParam) > 0;
	}

	@Override
	public void update(User user) {
		ShardParam shardParam = new ShardParam("shard_user", user.getId(), user);

		getSqlSession().update("user.update", shardParam);
	}
}
