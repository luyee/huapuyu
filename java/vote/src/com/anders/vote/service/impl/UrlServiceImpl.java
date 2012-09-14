package com.anders.vote.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return null;
	}

}
