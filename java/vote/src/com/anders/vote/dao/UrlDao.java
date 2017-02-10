package com.anders.vote.dao;

import java.util.Set;

import com.anders.vote.bo.Url;

public interface UrlDao extends GenericDao<Long, Url> {
	Set<Url> getAllFetchRoles();
}
