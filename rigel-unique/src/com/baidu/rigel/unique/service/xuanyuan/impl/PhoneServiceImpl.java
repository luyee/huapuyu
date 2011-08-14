package com.baidu.rigel.unique.service.xuanyuan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;
import com.baidu.rigel.unique.dao.xuanyuan.PhoneDao;
import com.baidu.rigel.unique.service.xuanyuan.PhoneService;
import com.baidu.rigel.unique.utils.Utils;

@Service("phoneService")
public class PhoneServiceImpl extends GenericSqlMapServiceImpl<Phone, Long> implements PhoneService {
	@Autowired
	private PhoneDao phoneDao;

	public List<Long> selectDisCustIdByFullPhone(String fullPhone) {
		if (Utils.isNull(fullPhone))
			return new ArrayList<Long>(0);
		return phoneDao.selectDisCustIdByFullPhone(fullPhone);
	}

	public List<Map<String, Object>> selectDisCustIdFullNameByPhoneNumAreaCode(String phoneAreaCode, String phoneNumber) {
		if (Utils.isNull(phoneNumber))
			return new ArrayList<Map<String, Object>>(0);
		if (StringUtils.isEmpty(phoneAreaCode))
			phoneAreaCode = null;
		return phoneDao.selectDisCustIdFullNameByPhoneNumAreaCode(phoneAreaCode, phoneNumber);
	}

	@Override
	public SqlMapDao<Phone, Long> getDao() {
		return phoneDao;
	}
}
