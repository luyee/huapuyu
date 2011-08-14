package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.unique.service.AuditService;
import com.baidu.rigel.unique.service.pangu.CustService;
import com.baidu.rigel.unique.service.xuanyuan.CustomerService;
import com.baidu.rigel.unique.service.xuanyuan.PhoneService;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.FieldConstant;
import com.baidu.rigel.unique.utils.Utils;

@Service("auditService")
public class AuditServiceImpl implements AuditService {
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private CustService custService;
	@Autowired
	private CustomerService customerService;

	public List<Map<String, Object>> listMatchCustUrl(String url) {
		List<Map<String, Object>> custMap = customerService.equalCustUrlName(url);
		custMap.addAll(custService.findBySiteUrl(url));
		return custMap;
	}

	public List<Map<String, Object>> listPreMatchCustUrl(String url, int count) {
		List<Map<String, Object>> custMap = customerService.containCustUrlName(url, count);
		custMap.addAll(custService.findBySiteUrl(url, count));
		return custMap;
	}

	public List<Map<String, Object>> listMatchDomain(String domain) {
		List<Map<String, Object>> custMap = customerService.equalDomain(domain);
		custMap.addAll(custService.findBySiteDomain(domain));
		return custMap;
	}

	public List<Map<String, Object>> listPreMatchDomain(String domain) {
		List<Map<String, Object>> custMap = customerService.containDomain(domain);
		custMap.addAll(custService.findBySiteDomain(domain, 10));
		return custMap;
	}

	public List<Long> listMatchPhoneContact(String fullPhone) {
		if (Utils.isNull(fullPhone))
			return new ArrayList<Long>(0);
		List<Long> custIdList = phoneService.selectDisCustIdByFullPhone(fullPhone);
		List<Map<String, Object>> custMap = custService.findByFullPhone(fullPhone);
		for (Map<String, Object> cust : custMap) {
			custIdList.add((Long) cust.get(FieldConstant.CUST_ID));
		}
		return custIdList;
	}

	public List<Map<String, Object>> listMatchPhoneContactMap(String phoneAreaCode, String phoneNumber) {
		if (Utils.isNull(phoneNumber))
			return new ArrayList<Map<String, Object>>(0);
		List<Map<String, Object>> custMap = phoneService.selectDisCustIdFullNameByPhoneNumAreaCode(phoneAreaCode, phoneNumber);
		String fullPhone = StringUtils.isNotBlank(phoneAreaCode) ? phoneAreaCode + Constant.HYPHEN + phoneNumber : phoneNumber;
		custMap.addAll(custService.findByFullPhone(fullPhone));
		return custMap;
	}

}
