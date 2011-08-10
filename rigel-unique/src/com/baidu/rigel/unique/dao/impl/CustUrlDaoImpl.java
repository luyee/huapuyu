package com.baidu.rigel.unique.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.CustUrl;
import com.baidu.rigel.unique.dao.CustUrlDao;
import com.baidu.rigel.unique.dao.CustUrlMapper;

@Service("custUrlDao")
public class CustUrlDaoImpl extends BaseSqlMapDao<CustUrl, Long> implements CustUrlDao {
	@Autowired
	private CustUrlMapper custUrlMapper;

	@Override
	public DaoMapper<CustUrl, Long> getDaoMapper() {
		return custUrlMapper;
	}

	public List<CustUrl> selectCustUrlByCustId(Long custId) {
		return custUrlMapper.selectCustUrlByCustId(custId);
	}
}