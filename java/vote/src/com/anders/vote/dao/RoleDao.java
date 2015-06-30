package com.anders.vote.dao;

import java.util.Set;

import com.anders.vote.bo.Role;

public interface RoleDao extends GenericDao<Long, Role> {
	Set<String> getRolesByUserName(String userName);
}
