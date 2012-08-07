package com.anders.crm.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.web.access.intercept.UrlService;
import org.springframework.stereotype.Service;

import com.anders.crm.bo.Url;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UrlDao;

@Service
public class UrlServiceImpl extends GenericServiceImpl<Long, Url> implements UrlService {

	private UrlDao urlDao;

	@Override
	public GenericDao<Long, Url> getDao() {
		return urlDao;
	}

	public UrlDao getUrlDao() {
		return urlDao;
	}

	public void setUrlDao(UrlDao urlDao) {
		this.urlDao = urlDao;
	}

	public Map<String, Set<String>> getUrlWithRoles() {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		Set<String> set = new HashSet<String>();
		set.add("ROLE_USER");
		set.add("ROLE_ADMIN");
		map.put("/test.do", set);
		return map;
	}

}
