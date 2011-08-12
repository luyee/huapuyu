package com.baidu.rigel.unique.dao.pangu;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.pangu.CustContactPhone;

public interface CustContactPhoneDao extends SqlMapDao<CustContactPhone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);
}