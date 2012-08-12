package com.anders.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.Role;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.RoleDao;
import com.anders.crm.service.RoleService;

//@Service("roleService")
@Service
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<Long, Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public GenericDao<Long, Role> getDao() {
		return roleDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Transactional(readOnly = true)
	public List<Role> getRolesByUsername(String username) {
		// Assert.hasText(username, "username is blank");
		if (StringUtils.isBlank(username)) {
			logger.warn("username is blank");
			return new ArrayList<Role>(0);
		}
		// TODO Anders Zhu 需要考虑user禁用或锁定等情况
		return getDao().find("select role from Role role inner join role.users user where role.id = user.id and role.enabled = true and user.enabled = true and user.username = ?", username);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<String> getRoleNames() {
		return getDao().findBy(Role.ENABLED, true, Role.NAME);
	}
}
