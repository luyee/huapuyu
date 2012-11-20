package com.anders.ssh.model.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	private static final long serialVersionUID = 5989698534331721397L;

	private Long id;
	/**
	 * login name, unique
	 */
	private String userName;
	/**
	 * user name, for example: Anders Zhu
	 */
	private String name;
	private String pwd;
	private Boolean enable = true;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<UserGroup> userGroups = new HashSet<UserGroup>(0);
	private Set<Resource> resources = new HashSet<Resource>(0);

	// ********************************************
	// following is UserDetails implement functions
	// ********************************************

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<String> roleNameSet = new HashSet<String>();
		for (Role role : roles)
			roleNameSet.add(role.getName());

		for (UserGroup userGroup : userGroups)
			for (Role role : userGroup.getRoles())
				roleNameSet.add(role.getName());

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		for (String roleName : roleNameSet)
			grantedAuthorityList.add(new GrantedAuthorityImpl(roleName));
		return grantedAuthorityList;

	}

	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enable;
	}

	// *****************
	// getter and setter
	// *****************

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

}
