package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;

public interface PhoneDao extends SqlMapDao<Phone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);

	public List<Map<String, Object>> selectDisrowCustIdFullNameByPhoneNumAreaCode(String phoneAreaCode, String phoneNumber);

	public List<Phone> selectPhoneByCustContactId(Long custContactId);
}