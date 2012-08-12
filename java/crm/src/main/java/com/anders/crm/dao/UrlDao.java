package com.anders.crm.dao;

import java.util.List;

import com.anders.crm.bo.Url;

public interface UrlDao extends GenericDao<Long, Url> {

	public List<Url> getUrlsFetchRoles();
}
