package com.baidu.rigel.unique.dao.pangu.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.pangu.CustContactPhone;
import com.baidu.rigel.unique.dao.pangu.CustContactPhoneDao;
import com.baidu.rigel.unique.dao.pangu.CustContactPhoneMapper;

@Service("custContactPhoneDao")
public class CustContactPhoneDaoImpl extends BaseSqlMapDao<CustContactPhone, Long> implements CustContactPhoneDao {
	@Autowired
	private CustContactPhoneMapper custContactPhoneMapper;

	@Override
	public DaoMapper<CustContactPhone, Long> getDaoMapper() {
		return custContactPhoneMapper;
	}

	public List<Long> selectDisCustIdByFullPhone(String fullPhone) {
		return custContactPhoneMapper.selectDisCustIdByFullPhone(fullPhone);
	}
}