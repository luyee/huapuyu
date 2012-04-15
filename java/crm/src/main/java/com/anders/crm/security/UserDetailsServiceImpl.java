package com.anders.crm.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anders.crm.bo.User;
import com.anders.crm.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			User user = userService.getByUsername(username);

			List<String> roleNameList = userService.getRoleNamesByUsername(username);
			if (CollectionUtils.isEmpty(roleNameList))
				return user;

			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			for (String roleName : roleNameList)
				grantedAuthorities.add(new GrantedAuthorityImpl(roleName));

			user.setAuthorities(grantedAuthorities);

			return user;
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
