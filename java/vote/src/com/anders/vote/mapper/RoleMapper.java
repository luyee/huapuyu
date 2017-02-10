package com.anders.vote.mapper;

import java.util.Set;

import com.anders.vote.bo.Role;

public interface RoleMapper extends GenericMapper<Long, Role> {
	Set<String> getRolesByUserName(String userName);
}
