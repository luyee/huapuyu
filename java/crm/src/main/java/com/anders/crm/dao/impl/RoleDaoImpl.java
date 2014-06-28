package com.anders.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.anders.crm.bo.Role;
import com.anders.crm.dao.RoleDao;

//@Repository("roleDao")
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Long, Role> implements RoleDao {
	public List<Role> getRolesByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			logger.warn("username is blank");
			return new ArrayList<Role>(0);
		}

		String hql = "select role from Role role inner join role.users user where role.enabled = true and user.enabled = true and user.username = ?";
		return find(hql, username);
	}
}
