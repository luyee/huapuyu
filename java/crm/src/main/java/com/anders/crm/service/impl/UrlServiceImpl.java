package com.anders.crm.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.intercept.UrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.Role;
import com.anders.crm.bo.Url;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UrlDao;

//@Service("urlService")
@Service
@Transactional
public class UrlServiceImpl extends GenericServiceImpl<Long, Url> implements UrlService {

	@Autowired
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

	@Transactional(readOnly = true)
	public Map<String, Set<String>> getUrlWithRoleNames() {
		List<Url> urlList = urlDao.getUrlsFetchRoles();

		Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();
		if (CollectionUtils.isEmpty(urlList)) {
			return resultMap;
		}

		for (Url url : urlList) {
			if (CollectionUtils.isEmpty(url.getRoles())) {
				continue;
			}

			Set<String> roleSet = new HashSet<String>();
			for (Role role : url.getRoles()) {
				roleSet.add(role.getName());
			}
			resultMap.put(url.getUrl(), roleSet);
		}

		return resultMap;
	}
}
