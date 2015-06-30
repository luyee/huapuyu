package com.baidu.rigel.unique.service.xuanyuan.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;
import com.baidu.rigel.unique.dao.xuanyuan.CustContactDao;
import com.baidu.rigel.unique.service.xuanyuan.CustContactService;
import com.baidu.rigel.unique.utils.Utils;

@Service("custContactService")
public class CustContactServiceImpl extends GenericSqlMapServiceImpl<CustContact, Long> implements CustContactService {
	@Autowired
	private CustContactDao custContactDao;

	public List<CustContact> selectCustContactByCustId(Long custId) {
		if (Utils.isNull(custId) || Utils.isLessThanZero(custId))
			return new ArrayList<CustContact>(0);
		return custContactDao.selectCustContactByCustId(custId);
	}

	@Override
	public SqlMapDao<CustContact, Long> getDao() {
		return custContactDao;
	}
}
