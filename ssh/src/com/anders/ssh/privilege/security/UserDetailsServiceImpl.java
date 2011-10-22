package com.anders.ssh.privilege.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anders.ssh.model.annotation.User;
import com.anders.ssh.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		try {
			User user = userService.getByUserName(userName);

			List<String> roleNameList = userService.getRoleNameListByUserName(userName);
			if (CollectionUtils.isEmpty(roleNameList))
				return user;

			List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
			for (String roleName : roleNameList)
				grantedAuthorityList.add(new GrantedAuthorityImpl(roleName));

			user.setAuthorities(grantedAuthorityList);

			return user;
		}
		catch (RuntimeException e) {
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
