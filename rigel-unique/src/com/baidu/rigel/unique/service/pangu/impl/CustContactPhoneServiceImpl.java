package com.baidu.rigel.unique.service.pangu.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.pangu.CustContactPhone;
import com.baidu.rigel.unique.dao.pangu.CustContactPhoneDao;
import com.baidu.rigel.unique.service.pangu.CustContactPhoneService;

@Service("custContactPhoneService")
public class CustContactPhoneServiceImpl extends GenericSqlMapServiceImpl<CustContactPhone, Long> implements CustContactPhoneService {
	@Resource(name = "custContactPhoneDao")
	private CustContactPhoneDao custContactPhoneDao;

	@Override
	public SqlMapDao<CustContactPhone, Long> getDao() {
		return custContactPhoneDao;
	}
}