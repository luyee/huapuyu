package com.anders.crm.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.User;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UserDao;
import com.anders.crm.service.UserService;
import com.anders.crm.utils.SecurityUtil;

//@Service("userService")
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ResourceBundleMessageSource rbms;

	@Override
	public GenericDao<Long, User> getDao() {
		return userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ResourceBundleMessageSource getRbms() {
		return rbms;
	}

	public void setRbms(ResourceBundleMessageSource rbms) {
		this.rbms = rbms;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank");
		}
		return getDao().findUniqueBy(User.USERNAME, username);
	}

	@Override
	@Transactional(readOnly = true)
	public String getNameByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank");
		}
		return getUserDao().getNameByUsername(username);
	}

	@Override
	public void updatePasswordToDefault(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank");
		}

		User user = getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("%s is not exist", username));
		}

		user.setPassword(SecurityUtil.randomPassword());
		user.setUpdateTime(new Date());
		user.setUpdateUser(getUserByUsername("zhuzhen"));
		getDao().update(user);
	}
}
