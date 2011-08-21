package com.anders.ssh.privilege.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.anders.ssh.model.Resource;
import com.anders.ssh.model.Role;
import com.anders.ssh.service.ResourceService;


public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource
{
	@Autowired
	private ResourceService resourceService;

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

	public FilterInvocationSecurityMetadataSourceImpl()
	{
	}

	public ResourceService getResourceService()
	{
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@PostConstruct
	public void loadResource()
	{
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		Collection<ConfigAttribute> caList;
		List<Resource> resourceList = resourceService.getAll();
		for (Resource resource : resourceList)
		{
			caList = new ArrayList<ConfigAttribute>();
			for (Role role : resource.getRoleSet())
				caList.add(new SecurityConfig(role.getName()));
			resourceMap.put(resource.getUrl(), caList);
		}
	}

}
