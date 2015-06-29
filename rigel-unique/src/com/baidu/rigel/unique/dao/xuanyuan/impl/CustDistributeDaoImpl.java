package com.baidu.rigel.unique.dao.xuanyuan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.xuanyuan.CustDistribute;
import com.baidu.rigel.unique.dao.xuanyuan.CustDistributeDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustDistributeMapper;

@Service("custDistributeDao")
public class CustDistributeDaoImpl extends BaseSqlMapDao<CustDistribute, Long> implements CustDistributeDao {
	@Autowired
	private CustDistributeMapper custDistributeMapper;

	@Override
	public DaoMapper<CustDistribute, Long> getDaoMapper() {
		return custDistributeMapper;
	}
}