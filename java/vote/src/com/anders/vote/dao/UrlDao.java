package com.anders.vote.dao;

import java.util.List;

import com.anders.vote.bo.Url;

public interface UrlDao extends GenericDao<Long, Url> {
	List<Url> getAllFetchRoles();
}
