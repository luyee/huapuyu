package security1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import service.interf.ResourceService;
import service.interf.UserService;

public class UserDetailsServiceImpl implements UserDetailsService
{
	private UserService userService;
	private ResourceService resourceService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException
	{
		return userService.getByUserName(userName);
	}

	public Map<String, String> loadUrlAuthorities()
	{
		Map<String, String> urlAuthorities = new HashMap<String, String>();
		List<Resource> urlResources = resourceService.getByType("URL");
		for (Resource resource : urlResources)
		{
			urlAuthorities.put(resource.getValue(), resource.getRoleAuthorities());
		}
		return urlAuthorities;
	}
}
