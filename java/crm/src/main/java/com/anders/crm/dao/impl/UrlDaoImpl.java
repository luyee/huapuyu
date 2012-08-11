package com.anders.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.Url;
import com.anders.crm.dao.UrlDao;

//@Repository("urlDao")
@Repository
public class UrlDaoImpl extends GenericDaoImpl<Long, Url> implements UrlDao {
}
