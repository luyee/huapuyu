package com.baidu.rigel.unique.service.xuanyuan.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;
import com.baidu.rigel.unique.dao.xuanyuan.CustUrlDao;
import com.baidu.rigel.unique.service.xuanyuan.CustUrlService;
import com.baidu.rigel.unique.utils.Utils;

@Service("custUrlService")
public class CustUrlServiceImpl extends GenericSqlMapServiceImpl<CustUrl, Long> implements CustUrlService {
	@Autowired
	private CustUrlDao custUrlDao;

	public List<CustUrl> findCustUrlByCustId(Long custId) {
		if (Utils.isNull(custId) || Utils.isLessThanZero(custId))
			return new ArrayList<CustUrl>(0);
		return custUrlDao.selectCustUrlByCustId(custId);
	}

	@Override
	public SqlMapDao<CustUrl, Long> getDao() {
		return custUrlDao;
	}
}
