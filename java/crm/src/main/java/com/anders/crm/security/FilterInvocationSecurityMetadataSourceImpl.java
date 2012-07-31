package com.anders.crm.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;

import com.anders.crm.service.RoleService;
import com.anders.crm.service.UrlService;

//public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
public class FilterInvocationSecurityMetadataSourceImpl extends DefaultFilterInvocationSecurityMetadataSource {

	private Logger logger = LoggerFactory.getLogger(FilterInvocationSecurityMetadataSourceImpl.class);

	@Autowired
	private UrlService urlService;
	@Autowired
	private RoleService roleService;

	public FilterInvocationSecurityMetadataSourceImpl() {
		super(new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>());
	}

	public FilterInvocationSecurityMetadataSourceImpl(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
		super(requestMap);
	}

	// private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	// private AnyRequestMatcher anyRequestMatcher;

	// TODO Anders Zhu : 是否要考虑并发集合类
	private static Map<String, Collection<ConfigAttribute>> configAttributeMap = new HashMap<String, Collection<ConfigAttribute>>();

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		List<String> roleNames = roleService.getRoleNames();
		if (CollectionUtils.isEmpty(roleNames))
			return null;

		Set<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>();
		for (String roleName : roleNames)
			configAttributes.add(new SecurityConfig(roleName));
		return configAttributes;
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (MapUtils.isEmpty(configAttributeMap)) {
			logger.warn("configAttributeMap is empty");
			return null;
		}

		// FilterInvocation: URL: /xxx/login.jsp
		String url = ((FilterInvocation) object).getRequestUrl();
		// TODO Anders Zhu : 添加url格式验证
		if (StringUtils.isBlank(url)) {
			logger.warn("url is blank");
			return null;
		}

		// Iterator<String> it = resourceMap.keySet().iterator();
		// while (it.hasNext()) {
		// String resUrl = it.next();
		// // TODO Anders Zhu : 修改
		// if (urlMatcher.pathMatchesUrl(url, resUrl)) {
		// return resourceMap.get(resUrl);
		// }
		// }
		return configAttributeMap.get(url);
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@PostConstruct
	public void loadAttributes() {
		// Collection<ConfigAttribute> configAttributes;
		// List<Resource> resourceList = resourceService.getAll();
		// List<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		// for (Url resource : resourceList) {
		// caList = new ArrayList<ConfigAttribute>();
		// for (Role role : resource.getRoles())
		// caList.add(new SecurityConfig(role.getName()));
		// resourceMap.put(resource, caList);
		// }
	}

	public UrlService getUrlService() {
		return urlService;
	}

	public void setUrlService(UrlService urlService) {
		this.urlService = urlService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
