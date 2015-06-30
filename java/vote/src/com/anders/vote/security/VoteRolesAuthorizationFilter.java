package com.anders.vote.security;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoteRolesAuthorizationFilter extends AuthorizationFilter {

	private static transient final Logger log = LoggerFactory.getLogger(VoteRolesAuthorizationFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;

		// if (rolesArray == null || rolesArray.length == 0) {
		if (ArrayUtils.isEmpty(rolesArray)) {
			return false;
		}

		Set<String> roles = CollectionUtils.asSet(rolesArray);
		for (String role : roles) {
			if (subject.hasRole(role)) {
				return true;
			}
		}

		return false;
	}

	// @Override
	// protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
	// Subject subject = getSubject(request, response);
	// // If the subject isn't identified, redirect to login URL
	// if (subject.getPrincipal() == null) {
	// saveRequestAndRedirectToLogin(request, response);
	// }
	// else {
	// // If subject is known but not authorized, redirect to the unauthorized URL if there is one
	// // If no unauthorized URL is specified, just return an unauthorized HTTP status code
	// String unauthorizedUrl = getUnauthorizedUrl();
	// // SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
	// if (StringUtils.hasText(unauthorizedUrl)) {
	// WebUtils.issueRedirect(request, response, unauthorizedUrl);
	// }
	// else {
	// WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	// }
	// }
	// return false;
	// }

}
