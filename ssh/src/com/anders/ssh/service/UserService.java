package com.anders.ssh.service;

import java.util.List;

import com.anders.ssh.model.annotation.User;

public interface UserService {
	public User getById(Long id);

	public void save(User user);

	public void update(User user);

	public void delete(User user);

	public void deleteById(Long id);

	public List<User> getAll();

	public User getByUserName(String userName);

	public List<String> getRoleNameListByUserName(String userName);
}