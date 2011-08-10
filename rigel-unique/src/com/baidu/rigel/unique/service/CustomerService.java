package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.Customer;
import com.baidu.rigel.unique.utils.CustType;

public interface CustomerService extends GenericService<Customer, Long> {
	public List<Long> equalCustFullName(String custFullName);

	public List<Long> equalCustBranchNameOrCustName(String name, CustType custType);

	public Map<Long, Long> findCustIdPosIdByCustIds(Long... custIds);

	public Map<Long, String> findCustIdFullNameByCustIds(Long... custIds);

	public boolean isCustIdsExist(List<Long> custIdList, Long posId, Long custId);

	public List<Map<String, Object>> findCustomerFollowDistributeByCustIdList(List<Long> custIdList);

	public Map<String, Object> findCustIdFullNamePoseIdInputTypeByCustId(Long custId);

	public List<Map<String, Object>> containCustUrlName(String custUrlName, int count);

	public List<Map<String, Object>> equalCustUrlName(String custUrlName);

	public List<Map<String, Object>> equalDomain(String domain);

	public List<Map<String, Object>> containDomain(String domain);

	public void delete(Long custId);
}
