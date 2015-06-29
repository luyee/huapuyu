package com.baidu.rigel.unique.dao.pangu;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.pangu.CustContactPhone;

@SqlMapper
public interface CustContactPhoneMapper extends DaoMapper<CustContactPhone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);
}