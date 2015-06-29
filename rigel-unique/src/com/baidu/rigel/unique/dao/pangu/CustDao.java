package com.baidu.rigel.unique.dao.pangu;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.pangu.Cust;

public interface CustDao extends SqlMapDao<Cust, Long> {
	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteUrl(String siteUrl);

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteDomain(String siteDomain);

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdByCustIdList(List<Long> custIdList);

	public List<Map<String, Object>> selectCustIdFullNameBySiteUrl(String siteUrl, int count);

	public List<Map<String, Object>> selectCustIdFullNameBySiteDomain(String siteDomain, int count);

	public List<Long> selectCustIdByFullName(String fullName);

	public List<Long> selectCustIdByBranch(String branch);

	public List<Long> selectCustIdByName(String name);

	public List<Map<String, Object>> selectCustIdStatInUcidFullNameByCustIdList(List<Long> custIdList);

	public List<Map<String, Long>> selectCustIdPosIdByCustIdList(List<Long> custIdList);

	public Long selectCountByCustIdList(List<Long> custIdList, Long posId, Long custId);
}