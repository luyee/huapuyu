package com.anders.vote.service;

import java.util.Set;

import com.anders.vote.bo.Role;

public interface RoleService extends GenericService<Long, Role> {

	Set<String> getRolesByUserName(String userName);
}
