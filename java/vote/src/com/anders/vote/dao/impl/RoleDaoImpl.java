package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.Role;
import com.anders.vote.dao.RoleDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.RoleMapper;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Long, Role> implements RoleDao {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public GenericMapper<Long, Role> getMapper() {
		return roleMapper;
	}

}
