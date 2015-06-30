package com.anders.vote.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anders.vote.bo.Role;
import com.anders.vote.bo.Url;
import com.anders.vote.dao.GenericDao;
import com.anders.vote.dao.UrlDao;
import com.anders.vote.service.UrlService;

@Service
public class UrlServiceImpl extends GenericServiceImpl<Long, Url> implements UrlService {

	@Autowired
	private UrlDao urlDao;

	@Override
	public GenericDao<Long, Url> getDao() {
		return urlDao;
	}

	@Override
	public Map<String, Set<String>> getUrlWithRoleNames() {
		Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();
		Set<Url> urls = urlDao.getAllFetchRoles();
		if (CollectionUtils.isEmpty(urls)) {
			return resultMap;
		}

		for (Url url : urls) {
			Set<String> roleNames = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(url.getRoles())) {
				for (Role role : url.getRoles()) {
					roleNames.add(role.getRole());
				}
			}
			resultMap.put(url.getUrl(), roleNames);
		}

		return resultMap;
	}
}
