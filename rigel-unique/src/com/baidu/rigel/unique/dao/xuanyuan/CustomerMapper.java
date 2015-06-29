package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;

@SqlMapper
public interface CustomerMapper extends DaoMapper<Customer, Long> {
	public List<Long> selectCustIdByCustFullName(String custFullName);

	public List<Long> selectCustIdByCustBranchName(Map<String, Object> paramMap);

	public List<Long> selectCustIdByCustName(Map<String, Object> paramMap);

	public List<Map<String, Long>> selectCustIdPoseIdByCustIdList(List<Long> custIdList);

	public List<Map<String, Object>> selectCustIdFullNameByCustIdList(List<Long> custIdList);

	public Long selectCountByCustIdListPoseIdCustId(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustomerFollowDistributeByCustIdList(List<Long> custIdList);

	public Map<String, Object> selectCustIdFullNamePoseIdInputTypeByCustId(Long custId);

	public List<Map<String, Object>> selectCustIdFullNameLikeByCustUrlName(Map<String, Object> paramMap);

	public List<Map<String, Object>> selectCustIdFullNameByCustUrlName(String custUrlName);

	public List<Map<String, Object>> selectCustIdFullNameByDomain(String domain);

	public List<Map<String, Object>> selectCustIdFullNameLikeByDomain(String domain);

	public Long selectCountByCustIdStat1(Map<String, Object> paramMap);
}