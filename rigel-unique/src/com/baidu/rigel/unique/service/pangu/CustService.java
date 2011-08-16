package com.baidu.rigel.unique.service.pangu;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.pangu.Cust;
import com.baidu.rigel.unique.utils.CustType;

public interface CustService extends GenericService<Cust, Long> {
	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteUrl(String siteUrl);

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdBySiteDomain(String siteDomain);

	public List<Map<String, Object>> selectCustIdTypeFullNamePosIdByCustIdList(String fullPhone);

	public List<Map<String, Object>> selectCustIdFullNameBySiteUrl(String siteUrl, int count);

	public List<Map<String, Object>> selectCustIdFullNameBySiteDomain(String siteDomain, int count);

	public List<Long> selectCustIdByFullName(String fullName);

	public List<Long> selectCustIdByNameOrBranch(String name, CustType custType);

	public List<Map<String, Object>> selectCustIdStatInUcidFullNameByCustIdList(List<Long> custIdList);

	public Map<Long, Long> selectCustIdPosIdByCustIdList(List<Long> custIdList);

	public boolean isCustIdListExist(List<Long> custIdList, Long posId, Long custId);
}