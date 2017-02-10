package com.anders.crm.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Deprecated
public class AccessDecisionManagerImpl implements AccessDecisionManager {

	private static Logger logger = LoggerFactory.getLogger(AccessDecisionManagerImpl.class);

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			logger.warn("configAttributes is null");
			return;
		}

		for (ConfigAttribute configAttribute : configAttributes) {
			String roleName = ((SecurityConfig) configAttribute).getAttribute();
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities())
				if (roleName.equals(grantedAuthority.getAuthority()))
					return;
		}

		throw new AccessDeniedException("have no authority");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
