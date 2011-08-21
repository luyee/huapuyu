package com.anders.ssh.service;

import java.util.List;

import com.anders.ssh.model.User;


public interface UserService
{
	public User getById(Integer id);

	public void save(User entity);

	public void update(User entity);

	public void delete(User entity);

	public void deleteById(Integer id);

	public List<User> getAll();

	public User getByUserName(String userName);
}