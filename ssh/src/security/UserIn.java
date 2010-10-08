package security;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.xml.Role;
import model.xml.User;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import dao.hibernate.UserDao;

public class UserIn implements UserDetailsService
{
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException
	{
		List<User> userList = userDao.loadAll();
		User userLogin = null;
		for (int i = 0; i < userList.size(); i++)
		{
			if (userList.get(i).getName().equals(name))
			{
				userLogin = userList.get(i);
			}
		}

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < userList.size(); i++)
		{
			User user = userList.get(i);
			Set<Role> userRoleSet = user.getRoles();
			Iterator<Role> iterator = userRoleSet.iterator();
			while (iterator.hasNext())
			{
				Role role = iterator.next();
				if (map.containsKey(user.getName()))
				{
					String value = role.getName();
					map.put(user.getName(), value + "," + map.get(user.getName()));
				}
				else
				{
					map.put(user.getName(), role.getName());
				}
			}

		}

		UserLoginDetails userDetails = new UserLoginDetails();
		if (userLogin != null)
		{ // 如果该用户的信息存在，那么构造UserDetails
			userDetails.setUsername(userLogin.getName()); // 设置用户名
			userDetails.setPassword(userLogin.getPwd()); // 设置密码
			userDetails.setEnabled(userLogin.getStatus() == 1 ? true : false); // 设置启用状态

			// 角色字符串如：”ROLE_ADMIN，ROLE_USER”。以逗号隔开
			String[] rights = map.get(name).split(","); // 分割多个角色
			// 设置用户的授权信息
			GrantedAuthority[] authorities = new GrantedAuthority[rights.length];
			for (int i = 0; i < rights.length; i++)
			{
				authorities[i] = new GrantedAuthorityImpl(rights[i]);
			}
			userDetails.setAuthorities(authorities);
		}
		else
		{
			throw new UsernameNotFoundException("User not found");
		}

		return userDetails;

	}
}
