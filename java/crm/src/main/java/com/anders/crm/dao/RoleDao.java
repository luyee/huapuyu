package com.anders.crm.dao;

import java.util.List;

import com.anders.crm.bo.Role;

public interface RoleDao extends GenericDao<Long, Role> {
	/**
	 * 根据用户名获取权限
	 * 
	 * @param username
	 *            用户名
	 * @return 权限列表
	 */
	public List<Role> getRolesByUsername(String username);
}
