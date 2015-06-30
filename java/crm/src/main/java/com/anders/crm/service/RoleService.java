package com.anders.crm.service;

import java.util.List;

import com.anders.crm.bo.Role;

public interface RoleService extends GenericService<Long, Role> {

	public List<Role> getRolesByUsername(String username);

	public List<String> getRoleNames();
}
