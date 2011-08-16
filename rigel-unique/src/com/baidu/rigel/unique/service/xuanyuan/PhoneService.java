package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;

public interface PhoneService extends GenericService<Phone, Long> {
	List<Long> selectDisCustIdByFullPhone(String fullPhone);

	List<Map<String, Object>> selectDisrowCustIdFullNameByPhoneNumAreaCode(String phoneAreaCode, String phoneNumber);

	List<Phone> selectPhoneByCustContactId(Long custContactId);
}
