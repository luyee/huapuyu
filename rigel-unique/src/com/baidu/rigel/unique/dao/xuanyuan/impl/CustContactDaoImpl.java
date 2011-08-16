package com.baidu.rigel.unique.dao.xuanyuan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;
import com.baidu.rigel.unique.dao.xuanyuan.CustContactDao;
import com.baidu.rigel.unique.dao.xuanyuan.CustContactMapper;

@Service("custContactDao")
public class CustContactDaoImpl extends BaseSqlMapDao<CustContact, Long> implements CustContactDao {
	@Autowired
	private CustContactMapper custContactMapper;

	@Override
	public DaoMapper<CustContact, Long> getDaoMapper() {
		return custContactMapper;
	}

	public List<CustContact> selectCustContactByCustId(Long custId) {
		return custContactMapper.selectCustContactByCustId(custId);
	}
}