package com.anders.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.dao.hibernate.UserDao;
import com.anders.ssh.model.annotation.User;
import com.anders.ssh.service.UserService;

@Component
@Transactional
public class UserServiceImpl implements UserService {
	@Resource(name = "hibernateUserDao")
	private UserDao userDao;

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public User getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public User getByUserName(String userName) {
		List<User> userList = userDao.find("FROM User user WHERE user.userName = ? AND user.enable = true", userName);
		if (CollectionUtils.isEmpty(userList))
			throw new RuntimeException("userList is empty");
		return userList.get(0);
	}

	@Override
	public List<String> getRoleNameListByUserName(String userName) {
		return userDao.getRoleNameListByUserName(userName);
	}
}
