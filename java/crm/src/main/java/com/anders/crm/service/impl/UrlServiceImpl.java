package com.anders.crm.service.impl;

import org.springframework.stereotype.Service;

import com.anders.crm.bo.Url;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UrlDao;
import com.anders.crm.service.UrlService;

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

}
