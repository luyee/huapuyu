package com.baidu.rigel.unique.dao.pangu.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.pangu.Cust;
import com.baidu.rigel.unique.dao.pangu.CustDao;
import com.baidu.rigel.unique.dao.pangu.CustMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("custDao")
public class CustDaoImpl extends BaseSqlMapDao<Cust, Long> implements CustDao {
	@Autowired
	private CustMapper custMapper;

	@Override
	public DaoMapper<Cust, Long> getDaoMapper() {
		return custMapper;
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteUrl(String siteUrl) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_URL, siteUrl);
		return custMapper.selectCustIdTypeFullNamePosId(paramMap);
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteDomain(String siteDomain) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_DOMAIN, siteDomain);
		return custMapper.selectCustIdTypeFullNamePosId(paramMap);
	}

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdByCustIdList(List<Long> custIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.LIST, custIdList);
		return custMapper.selectCustIdTypeFullNamePosId(paramMap);
	}

	public List<Map<String, Object>> selectCustIdFullNameBySiteUrl(String siteUrl, int count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_URL, siteUrl);
		paramMap.put(ParamConstant.COUNT, count);
		return custMapper.selectCustIdFullNameBySiteUrl(paramMap);
	}

	public List<Map<String, Object>> selectCustIdFullNameBySiteDomain(String siteDomain, int count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.SITE_DOMAIN, siteDomain);
		paramMap.put(ParamConstant.COUNT, count);
		return custMapper.selectCustIdFullNameBySiteDomain(paramMap);
	}

	public List<Long> selectCustIdByFullName(String fullName) {
		return custMapper.selectCustIdByFullName(fullName);
	}

	public List<Long> selectCustIdByBranch(String branch) {
		return custMapper.selectCustIdByBranch(branch);
	}

	public List<Long> selectCustIdByName(String name) {
		return custMapper.selectCustIdByName(name);
	}

	public List<Map<String, Object>> selectCustIdStatInUcidFullNameByCustIdList(List<Long> custIdList) {
		return custMapper.selectCustIdStatInUcidFullNameByCustIdList(custIdList);
	}

	public List<Map<String, Long>> selectCustIdPosIdByCustIdList(List<Long> custIdList) {
		return custMapper.selectCustIdPosIdByCustIdList(custIdList);
	}

	public Long selectCountByCustIdList(List<Long> custIdList, Long posId, Long custId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.LIST, custIdList);
		paramMap.put(ParamConstant.POS_ID, posId);
		paramMap.put(ParamConstant.CUST_ID, custId);
		return custMapper.selectCountByCustIdList(paramMap);
	}
}