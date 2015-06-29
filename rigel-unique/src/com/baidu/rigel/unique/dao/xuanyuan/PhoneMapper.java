package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;

@SqlMapper
public interface PhoneMapper extends DaoMapper<Phone, Long> {
	public List<Long> selectDisCustIdByFullPhone(String fullPhone);

	public List<Map<String, Object>> selectDisrowCustIdFullNameByPhoneNumAreaCode(Map<String, Object> paramMap);

	public List<Phone> selectPhoneByCustContactId(Long custContactId);
}