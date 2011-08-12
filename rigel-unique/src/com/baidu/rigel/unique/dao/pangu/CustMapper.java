package com.baidu.rigel.unique.dao.pangu;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.pangu.Cust;

@SqlMapper
public interface CustMapper extends DaoMapper<Cust, Long> {
	public List<Map<String, Object>> selectCustIdTypeFullNamePosId(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdFullNameBySiteUrl(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdFullNameBySiteDomain(Map<String, Object> paramMap);

	public List<Long> selectCustIdByFullName(String fullName);

	public List<Long> selectCustIdByBranch(String branch);

	public List<Long> selectCustIdByName(String name);

	public List<Map<String, Object>> selectCustIdStatInUcidFullNameByCustIdList(List<Long> list);

	public List<Map<String, Long>> selectCustIdPosIdByCustIdList(List<Long> list);

	public Long selectCountByCustIdList(Map<String, Object> paramMap);
}