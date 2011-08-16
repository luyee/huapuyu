package com.baidu.rigel.unique.dao.xuanyuan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;
import com.baidu.rigel.unique.dao.xuanyuan.PhoneDao;
import com.baidu.rigel.unique.dao.xuanyuan.PhoneMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("phoneDao")
public class PhoneDaoImpl extends BaseSqlMapDao<Phone, Long> implements PhoneDao {
	@Autowired
	private PhoneMapper phoneMapper;

	@Override
	public DaoMapper<Phone, Long> getDaoMapper() {
		return phoneMapper;
	}

	public List<Long> selectDisCustIdByFullPhone(String fullPhone) {
		return phoneMapper.selectDisCustIdByFullPhone(fullPhone);
	}

	public List<Map<String, Object>> selectDisrowCustIdFullNameByPhoneNumAreaCode(String phoneAreaCode, String phoneNumber) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.PHONE_AREA_CODE, phoneAreaCode);
		paramMap.put(ParamConstant.PHONE_NUMBER, phoneNumber);
		return phoneMapper.selectDisrowCustIdFullNameByPhoneNumAreaCode(paramMap);
	}
}