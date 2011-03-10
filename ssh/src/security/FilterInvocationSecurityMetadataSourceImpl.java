package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource
{
	// private RoleService roleService;
	// private ResourceService actionService;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes()
	{
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
	{
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext())
		{
			String resUrl = it.next();
			if (urlMatcher.pathMatchesUrl(url, resUrl))
			{
				return resourceMap.get(resUrl);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return true;
	}

	public void loadResourceDefine() throws Exception
	{
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		// for (TRole item : this.roleService.getAllRoles())
		// {
		// Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		// ConfigAttribute ca = new SecurityConfig(item.getRoleName());
		// atts.add(ca);
		// List<TAction> tActionList = actionService.findByRoleID(item.getRoleId());
		// // 把资源放入到spring security的resouceMap中
		// for (TAction tAction : tActionList)
		// {
		// this.resourceMap.put(tAction.getActionUrl(), atts);
		// }
		// }
		Collection<ConfigAttribute> caList = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN");
		caList.add(ca);
		resourceMap.put("/index.jsp", caList);
		resourceMap.put("/main.jsp", caList);
	}
}
