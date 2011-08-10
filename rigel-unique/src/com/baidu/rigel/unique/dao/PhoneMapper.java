package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.Phone;

@SqlMapper
public interface PhoneMapper extends DaoMapper<Phone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);

	public List<Map<String, Object>> selectDisCustIdFullNameByPhoneNumAreaCode(Map<String, Object> paramMap);
}