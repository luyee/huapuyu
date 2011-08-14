package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.utils.CustType;

public interface CustomerDao extends SqlMapDao<Customer, Long> {
	public List<Long> selectCustIdByCustFullName(String custFullName);

	public List<Long> selectCustIdByCustBranchName(String custBranchName, CustType custType);

	public List<Long> selectCustIdByCustName(String custName, CustType custType);

	public List<Map<String, Long>> selectCustIdPoseIdByCustIdList(List<Long> custIdList);

	public List<Map<String, Object>> selectCustIdFullNameByCustIdList(List<Long> custIdList);

	public Long selectCountByCustIdListPoseIdCustId(List<Long> custIdList, Long poseId, Long custId);

	public List<Map<String, Object>> selectCustomerFollowDistributeByCustIdList(List<Long> custIdList);

	public Map<String, Object> selectCustIdFullNamePoseIdInputTypeByCustId(Long custId);

	public List<Map<String, Object>> selectCustIdFullNameLikeByCustUrlName(String custUrlName, int count);

	public List<Map<String, Object>> selectCustIdFullNameByCustUrlName(String custUrlName);

	public List<Map<String, Object>> selectCustIdFullNameByDomain(String domain);

	public List<Map<String, Object>> selectCustIdFullNameLikeByDomain(String domain);

	public Long selectCountByCustIdStat1(Long custId, Integer custStat1);
}