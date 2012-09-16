package com.anders.vote.mapper;

import java.util.Set;

import com.anders.vote.bo.Url;

public interface UrlMapper extends GenericMapper<Long, Url> {
	public Set<Url> getAllFetchRoles();
}
