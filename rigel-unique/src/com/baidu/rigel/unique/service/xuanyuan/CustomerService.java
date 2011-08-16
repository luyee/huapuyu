package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.utils.CustType;

public interface CustomerService extends GenericService<Customer, Long> {
	public List<Long> selectCustIdByCustFullName(String custFullName);

	public List<Long> selectCustIdByCustBranchNameOrCustName(String name, CustType custType);

	public Map<Long, Long> selectCustIdPosIdByCustIds(Long... custIds);

	public Map<Long, String> selectCustIdFullNameByCustIds(Long... custIds);

	public boolean isCustIdsExist(List<Long> custIdList, Long posId, Long custId);

	public List<Map<String, Object>> selectCustomerFollowDistributeByCustIdList(List<Long> custIdList);

	public Map<String, Object> selectCustIdFullNamePoseIdInputTypeByCustId(Long custId);

	public List<Map<String, Object>> selectCustIdFullNameLikeByCustUrlName(String custUrlName, int count);

	public List<Map<String, Object>> selectCustIdFullNameByCustUrlName(String custUrlName);

	public List<Map<String, Object>> selectCustIdFullNameByDomain(String domain);

	public List<Map<String, Object>> selectCustIdFullNameLikeByDomain(String domain);

	public void delete(Long custId);
}
