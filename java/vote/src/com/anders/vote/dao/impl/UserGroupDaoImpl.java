package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.UserGroup;
import com.anders.vote.dao.UserGroupDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.UserGroupMapper;

@Repository
public class UserGroupDaoImpl extends GenericDaoImpl<Long, UserGroup> implements UserGroupDao {

	@Autowired
	private UserGroupMapper userGroupMapper;

	@Override
	public GenericMapper<Long, UserGroup> getMapper() {
		return userGroupMapper;
	}

}
