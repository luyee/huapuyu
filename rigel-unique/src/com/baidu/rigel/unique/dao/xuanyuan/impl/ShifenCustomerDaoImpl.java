package com.baidu.rigel.unique.dao.xuanyuan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;
import com.baidu.rigel.unique.dao.xuanyuan.ShifenCustomerDao;
import com.baidu.rigel.unique.dao.xuanyuan.ShifenCustomerMapper;
import com.baidu.rigel.unique.utils.ParamConstant;
import com.baidu.rigel.unique.utils.ReadConfig;
import com.baidu.rigel.unique.utils.Utils;

@Service("shifenCustomerDao")
public class ShifenCustomerDaoImpl extends BaseSqlMapDao<ShifenCustomer, Long> implements ShifenCustomerDao {
	@Autowired
	private ShifenCustomerMapper shifenCustomerMapper;
	@Autowired
	private ReadConfig readConfig;

	@Override
	public DaoMapper<ShifenCustomer, Long> getDaoMapper() {
		return shifenCustomerMapper;
	}

	public List<Map<String, Object>> selectCustIdNamesLikeBySiteUrl(String siteUrl, int count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_URL, siteUrl);
		paramMap.put(ParamConstant.COUNT, count);
		paramMap.put(ParamConstant.DAYS, readConfig.getDays());
		return shifenCustomerMapper.selectCustIdNamesLikeBySiteUrl(paramMap);
	}

	public List<Map<String, Object>> selectCustIdNamesBySiteUrl(String siteUrl) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_URL, siteUrl);
		paramMap.put(ParamConstant.INVALID_DATE, Utils.getInvalidDate(readConfig.getDays()));
		return shifenCustomerMapper.selectCustIdNamesBySiteUrl(paramMap);
	}

	public List<Long> selectCustIdByCompanyName(String companyName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.COMPANY_NAME, companyName);
		paramMap.put(ParamConstant.INVALID_DATE, Utils.getInvalidDate(readConfig.getDays()));
		return shifenCustomerMapper.selectCustIdByCompanyName(paramMap);
	}

	public List<Map<String, Object>> selectCustIdNamesByUrlDomain(String urlDomain) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.URL_DOMAIN, urlDomain);
		paramMap.put(ParamConstant.INVALID_DATE, Utils.getInvalidDate(readConfig.getDays()));
		return shifenCustomerMapper.selectCustIdNamesByUrlDomain(paramMap);
	}

	public List<Map<String, Object>> selectCustIdNamesLikeByUrlDomain(String urlDomain) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.URL_DOMAIN, urlDomain);
		paramMap.put(ParamConstant.INVALID_DATE, Utils.getInvalidDate(readConfig.getDays()));
		return shifenCustomerMapper.selectCustIdNamesLikeByUrlDomain(paramMap);
	}

	public List<ShifenCustomer> selectShifenCustomerByCustIdList(List<Long> custIdList) {
		return shifenCustomerMapper.selectShifenCustomerByCustIdList(custIdList);
	}

	public List<Long> selectCustIdByCompanyNameSiteUrl(String companyName, String siteUrl) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.COMPANY_NAME, companyName);
		paramMap.put(ParamConstant.REAL_COMPANY_NAME, companyName);
		paramMap.put(ParamConstant.SITE_URL, siteUrl);
		return shifenCustomerMapper.selectCustIdByCompanyNameSiteUrl(paramMap);
	}
}