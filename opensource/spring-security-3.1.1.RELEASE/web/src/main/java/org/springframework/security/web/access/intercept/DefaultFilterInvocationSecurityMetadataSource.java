/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.access.intercept;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.RequestMatcher;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.AnyRequestMatcher;
import org.springframework.security.web.access.expression.WebExpressionConfigAttribute;
import org.springframework.security.web.access.intercept.UrlService;

/**
 * Default implementation of <tt>FilterInvocationDefinitionSource</tt>.
 * <p>
 * Stores an ordered map of {@link RequestMatcher}s to <tt>ConfigAttribute</tt> collections and provides matching
 * of {@code FilterInvocation}s against the items stored in the map.
 * <p>
 * The order of the {@link RequestMatcher}s in the map is very important. The <b>first</b> one which matches the
 * request will be used. Later matchers in the map will not be invoked if a match has already been found.
 * Accordingly, the most specific matchers should be registered first, with the most general matches registered last.
 * <p>
 * The most common method creating an instance is using the Spring Security namespace. For example, the {@code pattern}
 * and {@code access} attributes of the {@code &lt;intercept-url&gt;} elements defined as children of the
 * {@code &lt;http&gt;} element are combined to build the instance used by the {@code FilterSecurityInterceptor}.
 *
 * @author Ben Alex
 * @author Luke Taylor
 */
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UrlService urlService;

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	private final ExpressionParser parser;

    //~ Constructors ===================================================================================================

    /**
     * Sets the internal request map from the supplied map. The key elements should be of type {@link RequestMatcher},
     * which. The path stored in the key will depend on
     * the type of the supplied UrlMatcher.
     *
     * @param requestMap order-preserving map of request definitions to attribute lists
     */
    public DefaultFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {

        this.requestMap = requestMap;
		this.parser = null;
    }
	
	public DefaultFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap, ExpressionParser parser) {

        this.requestMap = requestMap;
		this.parser = parser;
    }

    //~ Methods ========================================================================================================

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
	
	@PostConstruct
	public void loadAttributes() {
		Map<String, Set<String>> urlMap = urlService.getUrlWithRoleNames();
		
		if (parser == null) {
			if (urlMap != null && !urlMap.isEmpty()) {
				for (Map.Entry<String, Set<String>> entry : urlMap.entrySet()) {
					String url = entry.getKey();
					RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
					Set<ConfigAttribute> attributes = new HashSet<ConfigAttribute>(1);
					
					String roles = "";
					for (String role : entry.getValue()) {
						roles += role + ",";
					}
					
					if (StringUtils.hasText(roles)) {
						roles = roles.substring(0, roles.length() - 1);
						attributes.add(new SecurityConfig(roles));
						requestMap.put(requestMatcher, attributes);
					}
				}
			}
			
			for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
				RequestMatcher request = entry.getKey();
				String expression = entry.getValue().toArray(new ConfigAttribute[1])[0].getAttribute();
				logger.error("Adding web access control expression '" + expression + "', for " + request);
			}		
		} else {
			if (urlMap != null && !urlMap.isEmpty()) {
				for (Map.Entry<String, Set<String>> entry : urlMap.entrySet()) {
					String url = entry.getKey();
					RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
					Set<ConfigAttribute> attributes = new HashSet<ConfigAttribute>(1);
					
					String roles = "";
					for (String role : entry.getValue()) {
						roles += "'" + role + "',";
					}
					
					if (StringUtils.hasText(roles)) {
						roles = roles.substring(0, roles.length() - 1);
						roles = "hasAnyRole(" + roles + ")";
						
						try {
							attributes.add(new WebExpressionConfigAttribute(parser.parseExpression(roles)));
						} catch (ParseException e) {
							throw new IllegalArgumentException("Failed to parse expression");
						}
						
						requestMap.put(requestMatcher, attributes);
					}
				}
				
				RequestMatcher requestMatcher = new AnyRequestMatcher();
				Set<ConfigAttribute> attributes = new HashSet<ConfigAttribute>(1);
				try {
					attributes.add(new WebExpressionConfigAttribute(parser.parseExpression("denyAll")));
				} catch (ParseException e) {
					throw new IllegalArgumentException("Failed to parse expression");
				}
				requestMap.put(requestMatcher, attributes);
			}
			
			for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
				RequestMatcher request = entry.getKey();
				String expression = entry.getValue().toArray(new ConfigAttribute[1])[0].toString();
				logger.error("Adding web access control expression '" + expression + "', for " + request);
			}
		}
	}
	
	public UrlService getUrlService() {
		return urlService;
	}

	public void setUrlService(UrlService urlService) {
		this.urlService = urlService;
	}
}
