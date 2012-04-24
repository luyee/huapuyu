package com.anders.crm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;

import com.anders.crm.bo.Resource;
import com.anders.crm.bo.Role;
import com.anders.crm.service.ResourceService;
import com.anders.crm.utils.AntUrlPathMatcher;
import com.anders.crm.utils.UrlMatcher;

//public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
public class FilterInvocationSecurityMetadataSourceImpl extends DefaultFilterInvocationSecurityMetadataSource {
	public FilterInvocationSecurityMetadataSourceImpl(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
		super(requestMap);
	}

	public FilterInvocationSecurityMetadataSourceImpl() {
		super(new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>());
	}

	@Autowired
	private ResourceService resourceService;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// Set<ConfigAttribute> allAttributes = ;
		return null;
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// FilterInvocation: URL: /xxx/login.jsp
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String resUrl = it.next();
			// TODO Anders Zhu : 修改
			if (urlMatcher.pathMatchesUrl(url, resUrl)) {
				return resourceMap.get(resUrl);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	// public FilterInvocationSecurityMetadataSourceImpl() {
	// }

	@PostConstruct
	public void loadResource() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		Collection<ConfigAttribute> caList;
		// List<Resource> resourceList = resourceService.getAll();
		List<Resource> resourceList = new ArrayList<Resource>();
		for (Resource resource : resourceList) {
			caList = new ArrayList<ConfigAttribute>();
			for (Role role : resource.getRoles())
				caList.add(new SecurityConfig(role.getName()));
			resourceMap.put(resource.getContent(), caList);
		}
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

}
