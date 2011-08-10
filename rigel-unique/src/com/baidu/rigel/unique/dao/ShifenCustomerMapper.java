package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.ShifenCustomer;

@SqlMapper
public interface ShifenCustomerMapper extends DaoMapper<ShifenCustomer, Long> {
	public List<Map<String, Object>> selectCustIdNamesLikeBySiteUrl(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdNamesBySiteUrl(Map<String, Object> paramMap);

	public List<Long> selectCustIdByCompanyName(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdNamesByUrlDomain(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdNamesLikeByUrlDomain(Map<String, Object> paramMap);

	public List<ShifenCustomer> selectShifenCustomerByCustIdList(List<Long> custIdList);

	public List<Long> selectCustIdByCompanyNameSiteUrl(Map<String, Object> paramMap);
}