package com.anders.vote.dao.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.Url;
import com.anders.vote.dao.UrlDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.UrlMapper;

@Repository("urlDao")
public class UrlDaoImpl extends GenericDaoImpl<Long, Url> implements UrlDao {

	@Autowired
	private UrlMapper urlMapper;

	@Override
	public GenericMapper<Long, Url> getMapper() {
		return urlMapper;
	}

	@Override
	public Set<Url> getAllFetchRoles() {
		return urlMapper.getAllFetchRoles();
	}

}
