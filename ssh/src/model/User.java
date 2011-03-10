package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails
{
	private static final long serialVersionUID = 5989698534331721397L;

	private Integer id;
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
	private Set<Role> roleSet = new HashSet<Role>(0);
	private Set<UserGroup> userGroupSet = new HashSet<UserGroup>(0);
	private Set<Resource> resourceSet = new HashSet<Resource>(0);

	// ********************************************
	// following is UserDetails implement functions
	// ********************************************

	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		Set<String> roleNameSet = new HashSet<String>();
		for (Role role : roleSet)
			roleNameSet.add(role.getName());

		for (UserGroup userGroup : userGroupSet)
			for (Role role : userGroup.getRoleSet())
				roleNameSet.add(role.getName());

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		for (String roleName : roleNameSet)
			grantedAuthorityList.add(new GrantedAuthorityImpl(roleName));
		return grantedAuthorityList;

	}

	@Override
	public String getPassword()
	{
		return pwd;
	}

	@Override
	public String getUsername()
	{
		return userName;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return enable;
	}

	// *****************
	// getter and setter
	// *****************

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public Set<Role> getRoleSet()
	{
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet)
	{
		this.roleSet = roleSet;
	}

	public Set<UserGroup> getUserGroupSet()
	{
		return userGroupSet;
	}

	public void setUserGroupSet(Set<UserGroup> userGroupSet)
	{
		this.userGroupSet = userGroupSet;
	}

	public Set<Resource> getResourceSet()
	{
		return resourceSet;
	}

	public void setResourceSet(Set<Resource> resourceSet)
	{
		this.resourceSet = resourceSet;
	}
}
