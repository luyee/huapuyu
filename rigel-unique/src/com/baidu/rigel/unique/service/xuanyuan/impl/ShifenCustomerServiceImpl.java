package com.baidu.rigel.unique.service.xuanyuan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;
import com.baidu.rigel.unique.dao.xuanyuan.ShifenCustomerDao;
import com.baidu.rigel.unique.service.xuanyuan.ShifenCustomerService;
import com.baidu.rigel.unique.utils.Utils;

@Service("shifenCustomerService")
public class ShifenCustomerServiceImpl extends GenericSqlMapServiceImpl<ShifenCustomer, Long> implements ShifenCustomerService {
	@Autowired
	private ShifenCustomerDao shifenCustomerDao;

	public List<Map<String, Object>> containSiteUrl(String siteUrl, int count) {
		if (StringUtils.isBlank(siteUrl) || Utils.isEqualLessThanZero(count))
			return new ArrayList<Map<String, Object>>(0);
		List<Map<String, Object>> resultList = shifenCustomerDao.selectCustIdNamesLikeBySiteUrl(Utils.addRightWildcard(Utils.escapeWildcard(siteUrl)), count);
		if (CollectionUtils.isNotEmpty(resultList) && resultList.size() > count)
			resultList = resultList.subList(0, count);
		return resultList;
	}

	public List<Map<String, Object>> equalSiteUrl(String siteUrl) {
		if (StringUtils.isBlank(siteUrl))
			return new ArrayList<Map<String, Object>>(0);
		return shifenCustomerDao.selectCustIdNamesBySiteUrl(siteUrl);
	}

	public List<Long> equalCompanyName(String companyName) {
		if (StringUtils.isBlank(companyName))
			return new ArrayList<Long>(0);
		return shifenCustomerDao.selectCustIdByCompanyName(companyName);
	}

	public List<Map<String, Object>> equalUrlDomain(String urlDomain) {
		if (StringUtils.isBlank(urlDomain))
			return new ArrayList<Map<String, Object>>(0);
		return shifenCustomerDao.selectCustIdNamesByUrlDomain(urlDomain);
	}

	public List<Map<String, Object>> containUrlDomain(String urlDomain) {
		if (StringUtils.isBlank(urlDomain))
			return new ArrayList<Map<String, Object>>(0);
		return shifenCustomerDao.selectCustIdNamesLikeByUrlDomain(Utils.addRightWildcard(Utils.escapeWildcard(urlDomain)));
	}

	public List<ShifenCustomer> getShifenCustomerByCustIdList(List<Long> custIdList) {
		if (CollectionUtils.isEmpty(custIdList))
			return new ArrayList<ShifenCustomer>(0);
		return shifenCustomerDao.selectShifenCustomerByCustIdList(custIdList);
	}

	@Override
	public SqlMapDao<ShifenCustomer, Long> getDao() {
		return shifenCustomerDao;
	}

	public List<Long> selectCustIdByCompanyNameSiteUrl(String companyName, String siteUrl) {
		if (StringUtils.isBlank(companyName) || StringUtils.isBlank(siteUrl))
			return new ArrayList<Long>(0);
		return shifenCustomerDao.selectCustIdByCompanyNameSiteUrl(companyName, siteUrl);
	}

}
