package com.baidu.rigel.unique.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.rigel.unique.facade.CustomerFacade;
import com.baidu.rigel.unique.vo.CustomerVO;

@Service("customerFacade")
public class MockCustomerFacadeImpl implements CustomerFacade {

	public List<Map<String, Object>> findByUrl(String url) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public List<Map<String, Object>> findByDomain(String domain) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public List<Map<String, Object>> findByCustName(String custName) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public List<Map<String, Object>> findByPhone(String phone) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public List<Map<String, Object>> listPreMatchCustUrl(String url, int count) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public List<Map<String, Object>> listPreMatchCustDomain(String domain) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public CustomerVO getCustomerSimpleInfo(Long id) {
		return new CustomerVO();
	}

	public List<Long> listMatchCustBranchName(String custBranchName, Long custType) {
		return new ArrayList<Long>();
	}

	public List<Long> listMatchCustName(String custFullName) {
		return new ArrayList<Long>();
	}

	public Map<Long, Long> findCustPosIdById(Long... custIds) {
		return new HashMap<Long, Long>();
	}

	public Map<Long, String> findCustNameById(Long... custIds) {
		return new HashMap<Long, String>();
	}

	public Map<Long, CustomerVO> getCustomerSimpleInfos(Long... ids) {
		return new HashMap<Long, CustomerVO>();
	}

	public boolean findByCustIdsAndPosid(List<Long> custIdList, Long posid, Long custId) {
		return false;
	}

	public List<Map<String, Object>> findCustCompareInfoByCustIds(List<Long> custIdList) {
		return new ArrayList<Map<String, Object>>(0);
	}

	public boolean saveDistributeCust(CustomerVO customerVO, List<Map<String, Object>> postils) {
		return false;
	}

}
