package com.baidu.rigel.unique.service.pangu;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.pangu.Cust;
import com.baidu.rigel.unique.utils.CustType;

public interface CustService extends GenericService<Cust, Long> {
	public List<Map<String, Object>> findBySiteUrl(String siteUrl);

	public List<Map<String, Object>> findBySiteDomain(String siteDomain);

	public List<Map<String, Object>> findByFullPhone(String fullPhone);

	public List<Map<String, Object>> findBySiteUrl(String siteUrl, int count);

	public List<Map<String, Object>> findBySiteDomain(String siteDomain, int count);

	public List<Long> findByFullName(String fullName);

	public List<Long> findByNameOrBranch(String name, CustType custType);

	public List<Map<String, Object>> findCustIdStatInUcidFullNameByCustIdList(List<Long> custIdList);

	public Map<Long, Long> findCustIdPosIdByCustIdList(List<Long> custIdList);

	public boolean isCustIdListExist(List<Long> custIdList, Long posId, Long custId);
}