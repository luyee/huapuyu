package com.anders.crm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.Url;
import com.anders.crm.dao.UrlDao;

//@Repository("urlDao")
@Repository
public class UrlDaoImpl extends GenericDaoImpl<Long, Url> implements UrlDao {

	public List<Url> getUrlsFetchRoles() {
		String hql = "select url from Url url inner join url.roles role where url.enabled = true and role.enabled = true";
		return find(hql);
	}
}
