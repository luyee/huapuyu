package com.anders.vote.service;

import java.io.Serializable;

import com.anders.vote.dao.GenericDao;

public interface GenericService<PK extends Serializable, T> extends GenericDao<PK, T> {

	GenericDao<PK, T> getDao();
}
