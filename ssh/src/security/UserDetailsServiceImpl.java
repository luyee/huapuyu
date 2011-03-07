package security;

import java.util.HashSet;
import java.util.Set;

import model.Authority;
import model.Role;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService
{

	private AccountManager accountManager;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{

		User user = accountManager.findUserByLoginName(username);
		if (user == null)
		{
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		// -- mini-web示例中无以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userdetails = new org.springframework.security.core.userdetails.User(user.getName(), user.getPwd(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);

		return userdetails;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(User user)
	{
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles())
		{
			for (Authority authority : role.getAuthorities())
			{
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager)
	{
		this.accountManager = accountManager;
	}
}
