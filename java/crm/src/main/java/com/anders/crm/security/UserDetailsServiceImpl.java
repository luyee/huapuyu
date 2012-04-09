package com.anders.crm.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			User user = userService.getByUsername(username);

			List<String> roleNameList = userService.getRoleNameListByUsername(username);
			if (CollectionUtils.isEmpty(roleNameList))
				return user;

			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			for (String roleName : roleNameList)
				grantedAuthorities.add(new GrantedAuthorityImpl(roleName));

			user.setAuthorities(grantedAuthorities);

			return user;
		}
		catch (RuntimeException e) {
			// TODO Anders Zhu:
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
