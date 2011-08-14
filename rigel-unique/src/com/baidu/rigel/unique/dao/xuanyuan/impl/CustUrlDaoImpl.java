package com.baidu.rigel.unique.dao.xuanyuan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;
import com.baidu.rigel.unique.dao.xuanyuan.CustUrlDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustUrlMapper;

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