package com.anders.ssh.service;

import java.util.List;

import com.anders.ssh.bo.xml.Role;

public interface RoleService {
	public Role getById(Long id);

	public void save(Role role);

	public void update(Role role);

	public void delete(Role role);

	public void deleteById(Long id);

	public List<Role> getAll();
}