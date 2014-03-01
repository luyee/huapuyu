package com.anders.crm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.User;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UserDao;
import com.anders.crm.facade.MailFacade;
import com.anders.crm.service.UserService;
import com.anders.crm.utils.Constant;
import com.anders.crm.utils.MailType;
import com.anders.crm.utils.SecurityUtil;

//@Service("userService")
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MailFacade mailFacade;

	@Override
	public GenericDao<Long, User> getDao() {
		return userDao;
	}

	public UserDao getUserDao() {
		return userDao;
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
	public void updatePasswordToDefault(String locale, String username, String from, String subject) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank");
		}

		User user = getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("%s is not exist", username));
		}

		String randPassword = SecurityUtil.getRandomPassword();

		user.setPassword(SecurityUtil.getSha256Password(randPassword, username));
		user.setUpdateTime(new Date());
		user.setUpdateUser(getUserByUsername(Constant.ADMINISTRATOR_USERNAME));
		getDao().update(user);

		Map<String, Object> emailParams = new HashMap<String, Object>();
		emailParams.put("user", user);
		emailParams.put("password", randPassword);
		mailFacade.sendMail(MailType.GET_PASSWORD, locale, from, user.getEmail(), subject, emailParams);
	}

	@Override
	public boolean isExistByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank");
		}

		return userDao.isExistByUsername(username);
	}

	@Override
	public boolean isExistByEmail(String email) {
		if (StringUtils.isBlank(email)) {
			throw new IllegalArgumentException("email is blank");
		}

		return userDao.isExistByEmail(email);
	}

}
