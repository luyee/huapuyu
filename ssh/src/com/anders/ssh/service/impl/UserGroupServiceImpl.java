package com.anders.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.bo.annotation.UserGroup;
import com.anders.ssh.dao.hibernate.UserGroupDao;
import com.anders.ssh.service.UserGroupService;

@Component
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
	@Resource(name = "hibernateUserGroupDao")
	private UserGroupDao userGroupDao;

	@Override
	public void delete(UserGroup userGroup) {
		userGroupDao.delete(userGroup);
	}

	@Override
	public void deleteById(Long id) {
		userGroupDao.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public UserGroup getById(Long id) {
		return userGroupDao.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<UserGroup> getAll() {
		return userGroupDao.getAll();
	}

	@Override
	public void save(UserGroup userGroup) {
		userGroupDao.save(userGroup);
	}

	@Override
	public void update(UserGroup userGroup) {
		userGroupDao.update(userGroup);
	}
}
