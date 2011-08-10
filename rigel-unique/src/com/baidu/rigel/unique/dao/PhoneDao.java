package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.Phone;

public interface PhoneDao extends SqlMapDao<Phone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);

	public List<Map<String, Object>> selectDisCustIdFullNameByPhoneNumAreaCode(String phoneAreaCode, String phoneNumber);
}