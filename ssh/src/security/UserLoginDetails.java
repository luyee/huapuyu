package security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

public class UserLoginDetails implements UserDetails
{
	private static final long serialVersionUID = -971765342475233938L;

	private String username;
	private String password;
	private boolean enabled;
	private GrantedAuthority[] authorities;

	public void setAuthorities(GrantedAuthority[] authorities)
	{
		this.authorities = authorities;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public GrantedAuthority[] getAuthorities()
	{
		return authorities;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUsername()
	{
		return username;
	}

	public boolean isAccountNonExpired()
	{
		return enabled;
	}

	public boolean isAccountNonLocked()
	{
		return enabled;
	}

	public boolean isCredentialsNonExpired()
	{
		return enabled;
	}

	public boolean isEnabled()
	{
		return enabled;
	}
}