package com.anders.ssh.service;

import java.util.List;

import com.anders.ssh.bo.annotation.UserGroup;

public interface UserGroupService {
	public UserGroup getById(Long id);

	public void save(UserGroup userGroup);

	public void update(UserGroup userGroup);

	public void delete(UserGroup userGroup);

	public void deleteById(Long id);

	public List<UserGroup> getAll();
}