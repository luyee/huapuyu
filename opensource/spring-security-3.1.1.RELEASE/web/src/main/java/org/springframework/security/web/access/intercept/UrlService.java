package org.springframework.security.web.access.intercept;

import java.util.Map;
import java.util.Set;

public interface UrlService {
	public Map<String, Set<String>> getUrlWithRoles();
}
