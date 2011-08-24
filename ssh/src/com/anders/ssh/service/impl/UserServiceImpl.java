package com.anders.ssh.service.impl;

import java.util.List;


import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.dao.hibernate.UserDao;
import com.anders.ssh.model.xml.User;
import com.anders.ssh.service.UserService;


@Transactional
public class UserServiceImpl implements UserService
{
	private UserDao dao;

	public UserDao getDao()
	{
		return dao;
	}

	public void setDao(UserDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void delete(User entity)
	{
		dao.delete(entity);
	}

	@Override
	public void deleteById(Integer id)
	{
		dao.deleteById(id);
	}

	@Override
	@Transactional()
	public User getById(Integer id)
	{
		return dao.getById(id);
	}

	@Override
	public List<User> getAll()
	{
		return dao.getAll();
	}

	@Override
	public void save(User entity)
	{
		dao.save(entity);
	}

	@Override
	public void update(User entity)
	{
		dao.update(entity);
	}

	@Override
	public User getByUserName(String userName)
	{
		List<User> userList = dao.find("FROM User user WHERE user.userName = ? AND user.enable = true", userName);
		if (userList.isEmpty())
		{
			throw new RuntimeException("userList is empty!");
		}
		return userList.get(0);

	}
}
