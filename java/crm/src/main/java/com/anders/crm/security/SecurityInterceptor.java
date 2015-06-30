package com.anders.crm.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

@Deprecated
public class SecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	// private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
	private SecurityMetadataSource securityMetadataSource;

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		// return filterInvocationSecurityMetadataSource;
		return securityMetadataSource;
	}

	public SecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	// public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
	// return filterInvocationSecurityMetadataSource;
	// }
	//
	// public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
	// this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
	// }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
		InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
		try {
			filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
		}
		finally {
			super.afterInvocation(token, null);
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
