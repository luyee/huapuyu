package com.baidu.rigel.unique.service.pangu.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.pangu.Cust;
import com.baidu.rigel.unique.dao.pangu.CustContactPhoneDao;
import com.baidu.rigel.unique.dao.pangu.CustDao;
import com.baidu.rigel.unique.service.pangu.CustService;
import com.baidu.rigel.unique.utils.CustType;
import com.baidu.rigel.unique.utils.FieldConstant;
import com.baidu.rigel.unique.utils.ReadConfig;
import com.baidu.rigel.unique.utils.Utils;

@Service("custService")
public class CustServiceImpl extends GenericSqlMapServiceImpl<Cust, Long> implements CustService {
	@Resource(name = "custDao")
	private CustDao custDao;
	@Resource(name = "custContactPhoneDao")
	private CustContactPhoneDao custContactPhoneDao;
	@Autowired
	private ReadConfig readConfig;

	@Override
	public SqlMapDao<Cust, Long> getDao() {
		return custDao;
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteDomain(String siteDomain) {
		return custDao.selectCustIdTypeFullNamePosIdBySiteDomain(siteDomain);
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdByCustIdList(String fullPhone) {
		List<Long> custIdList = null;
		if (Utils.isNotNull(fullPhone))
			custIdList = custContactPhoneDao.selectDisCustIdByFullPhone(fullPhone);
		return custDao.selectCustIdTypeFullNamePosIdByCustIdList(custIdList);
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteUrl(String siteUrl) {
		return custDao.selectCustIdTypeFullNamePosIdBySiteUrl(siteUrl);
	}

	public List<Map<String, Object>> selectCustIdFullNameBySiteUrl(String siteUrl, int count) {
		if (StringUtils.isBlank(siteUrl) || Utils.isEqualLessThanZero(count))
			return new ArrayList<Map<String, Object>>(0);
		return custDao.selectCustIdFullNameBySiteUrl(Utils.addRightWildcard(Utils.escapeWildcard(siteUrl)), count);
	}

	public List<Map<String, Object>> selectCustIdFullNameBySiteDomain(String siteDomain, int count) {
		if (StringUtils.isBlank(siteDomain) || Utils.isEqualLessThanZero(count))
			return new ArrayList<Map<String, Object>>(0);
		return custDao.selectCustIdFullNameBySiteDomain(Utils.addRightWildcard(Utils.escapeWildcard(siteDomain)), count);
	}

	public List<Long> selectCustIdByFullName(String fullName) {
		return custDao.selectCustIdByFullName(fullName);
	}

	public List<Long> selectCustIdByNameOrBranch(String name, CustType custType) {
		switch (custType) {
		case ADVERTISING_AGENCY:
			return custDao.selectCustIdByBranch(name);
		case SPECIAL_ENTERPRISE:
			return custDao.selectCustIdByName(name);
		default:
			throw new IllegalArgumentException(String.format(readConfig.getErrorParamMsg(), "fullName"));
		}
	}

	public List<Map<String, Object>> selectCustIdStatInUcidFullNameByCustIdList(List<Long> custIdList) {
		if (CollectionUtils.isEmpty(custIdList))
			return new ArrayList<Map<String, Object>>(0);
		return custDao.selectCustIdStatInUcidFullNameByCustIdList(custIdList);
	}

	public Map<Long, Long> selectCustIdPosIdByCustIdList(List<Long> custIdList) {
		Map<Long, Long> map = new HashMap<Long, Long>();
		if (CollectionUtils.isEmpty(custIdList))
			return map;
		List<Map<String, Long>> list = custDao.selectCustIdPosIdByCustIdList(custIdList);
		if (CollectionUtils.isNotEmpty(list))
			for (Map<String, Long> m : list)
				map.put(m.get(FieldConstant.ID), m.get(FieldConstant.POSID));
		return map;
	}

	public boolean isCustIdListExist(List<Long> custIdList, Long posId, Long custId) {
		if (CollectionUtils.isEmpty(custIdList))
			return Boolean.FALSE;
		if (Utils.isNull(posId) || Utils.isLessThanZero(posId))
			posId = null;
		if (Utils.isNull(custId) || Utils.isLessThanZero(custId))
			custId = null;
		return Utils.isGreaterThanZero(custDao.selectCountByCustIdList(custIdList, posId, custId));
	}
}