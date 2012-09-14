package com.anders.vote.mapper;

import java.util.List;

import com.anders.vote.bo.Url;

public interface UrlMapper extends GenericMapper<Long, Url> {
	public List<Url> getAllFetchRoles();
}
