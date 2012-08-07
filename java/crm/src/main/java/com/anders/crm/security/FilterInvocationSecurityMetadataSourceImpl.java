package com.anders.crm.security;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.UrlService;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.util.Assert;

import com.anders.crm.service.RoleService;

//public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
//public class FilterInvocationSecurityMetadataSourceImpl extends DefaultFilterInvocationSecurityMetadataSource {
@Deprecated
public class FilterInvocationSecurityMetadataSourceImpl extends DefaultFilterInvocationSecurityMetadataSource {

	private Logger logger = LoggerFactory.getLogger(FilterInvocationSecurityMetadataSourceImpl.class);

	@Autowired
	private UrlService urlService;
	@Autowired
	private RoleService roleService;

	// public FilterInvocationSecurityMetadataSourceImpl(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
	// SecurityExpressionHandler<FilterInvocation> expressionHandler) {
	// super(new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>(), null);
	// FilterInvocationSecurityMetadataSourceParser
	// HttpConfigurationBuilder
	// }

	public FilterInvocationSecurityMetadataSourceImpl(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap, SecurityExpressionHandler<FilterInvocation> expressionHandler) {
		super(requestMap, null);
		Assert.notNull(expressionHandler, "A non-null SecurityExpressionHandler is required");
	}

	// public FilterInvocationSecurityMetadataSourceImpl(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
	// super(requestMap);
	// }

	// private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	// private AnyRequestMatcher anyRequestMatcher;

	private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

	// public Collection<ConfigAttribute> getAllConfigAttributes() {
	// List<String> roleNames = roleService.getRoleNames();
	// if (CollectionUtils.isEmpty(roleNames))
	// return new ArrayList<ConfigAttribute>();
	//
	// Set<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>();
	// for (String roleName : roleNames)
	// configAttributes.add(new SecurityConfig(roleName));
	// return configAttributes;
	// }
	//
	// object is : FilterInvocation: URL: /login.jsp
	// public Collection<ConfigAttribute> getAttributes(Object object) {
	// if (MapUtils.isEmpty(configAttributeMap)) {
	// logger.warn("configAttributeMap is empty");
	// return new ArrayList<ConfigAttribute>();
	// }
	//
	// // FilterInvocation: URL: /xxx/login.jsp
	// String url = ((FilterInvocation) object).getRequestUrl();
	// // TODO Anders Zhu : 添加url格式验证
	// if (StringUtils.isBlank(url)) {
	// logger.warn("url is blank");
	// return new ArrayList<ConfigAttribute>();
	// }
	//
	// // Iterator<String> it = resourceMap.keySet().iterator();
	// // while (it.hasNext()) {
	// // String resUrl = it.next();
	// // // TODO Anders Zhu : 修改
	// // if (urlMatcher.pathMatchesUrl(url, resUrl)) {
	// // return resourceMap.get(resUrl);
	// // }
	// // }
	// return configAttributeMap.get(url);
	//
	// final HttpServletRequest request = ((FilterInvocation) object).getRequest();
	// for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
	// if (entry.getKey().matches(request)) {
	// return entry.getValue();
	// }
	// }
	// return null;
	// }
	//
	// public boolean supports(Class<?> clazz) {
	// return FilterInvocation.class.isAssignableFrom(clazz);
	// }

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

		// List<ConfigAttribute> configAttributeList = new ArrayList<ConfigAttribute>();
		// configAttributeList.add(new SecurityConfig("denyAll"));
		// requestMap.put(new AnyRequestMatcher(), configAttributeList);
		//
		// configAttributeList = new ArrayList<ConfigAttribute>();
		// configAttributeList.add(new SecurityConfig("ROLE_USER"));
		// requestMap.put(new AntPathRequestMatcher("/login.jsp"), configAttributeList);
		System.out.println("hello world");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
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
