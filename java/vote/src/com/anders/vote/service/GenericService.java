package com.anders.vote.service;

import java.io.Serializable;

import com.anders.vote.dao.GenericDao;
import com.anders.vote.mapper.GenericMapper;

public interface GenericService<PK extends Serializable, T> extends GenericDao<PK, T> {
	GenericMapper<PK, T> getDao();
}
