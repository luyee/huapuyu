package com.anders.vote.service.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anders.vote.bo.Role;
import com.anders.vote.dao.GenericDao;
import com.anders.vote.dao.RoleDao;
import com.anders.vote.service.RoleService;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Long, Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public GenericDao<Long, Role> getDao() {
		return roleDao;
	}

	@Override
	public Set<String> getRolesByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("userName is blank");
		}
		return roleDao.getRolesByUserName(userName);
	}

}
