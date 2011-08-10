package com.baidu.rigel.unique.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.Customer;
import com.baidu.rigel.unique.dao.CustomerDao;
import com.baidu.rigel.unique.dao.CustomerMapper;
import com.baidu.rigel.unique.utils.CustType;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("customerDao")
public class CustomerDaoImpl extends BaseSqlMapDao<Customer, Long> implements CustomerDao {
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public DaoMapper<Customer, Long> getDaoMapper() {
		return customerMapper;
	}

	public List<Long> selectCustIdByCustFullName(String custFullName) {
		return customerMapper.selectCustIdByCustFullName(custFullName);
	}

	public List<Long> selectCustIdByCustBranchName(String custBranchName, CustType custType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_BRANCH_NAME, custBranchName);
		paramMap.put(ParamConstant.CUST_TYPE, custType.getValue());
		return customerMapper.selectCustIdByCustBranchName(paramMap);
	}

	public List<Long> selectCustIdByCustName(String custName, CustType custType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_NAME, custName);
		paramMap.put(ParamConstant.CUST_TYPE, custType.getValue());
		return customerMapper.selectCustIdByCustName(paramMap);
	}

	public List<Map<String, Long>> selectCustIdPoseIdByCustIdList(List<Long> custIdList) {
		return customerMapper.selectCustIdPoseIdByCustIdList(custIdList);
	}

	public List<Map<String, Object>> selectCustIdFullNameByCustIdList(List<Long> custIdList) {
		return customerMapper.selectCustIdFullNameByCustIdList(custIdList);
	}

	public Long selectCountByCustIdListPoseIdCustId(List<Long> custIdList, Long poseId, Long custId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.LIST, custIdList);
		paramMap.put(ParamConstant.POSE_ID, poseId);
		paramMap.put(ParamConstant.CUST_ID, custId);
		return customerMapper.selectCountByCustIdListPoseIdCustId(paramMap);
	}

	public List<Map<String, Object>> selectCustomerFollowDistributeByCustIdList(List<Long> custIdList) {
		return customerMapper.selectCustomerFollowDistributeByCustIdList(custIdList);
	}

	public Map<String, Object> selectCustIdFullNamePoseIdInputTypeByCustId(Long custId) {
		return customerMapper.selectCustIdFullNamePoseIdInputTypeByCustId(custId);
	}

	public List<Map<String, Object>> selectCustIdFullNameLikeByCustUrlName(String custUrlName, int count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_URL_NAME, custUrlName);
		paramMap.put(ParamConstant.COUNT, count);
		return customerMapper.selectCustIdFullNameLikeByCustUrlName(paramMap);
	}

	public List<Map<String, Object>> selectCustIdFullNameByCustUrlName(String custUrlName) {
		return customerMapper.selectCustIdFullNameByCustUrlName(custUrlName);
	}

	public List<Map<String, Object>> selectCustIdFullNameByDomain(String domain) {
		return customerMapper.selectCustIdFullNameByDomain(domain);
	}

	public List<Map<String, Object>> selectCustIdFullNameLikeByDomain(String domain) {
		return customerMapper.selectCustIdFullNameLikeByDomain(domain);
	}

	public Long selectCountByCustIdStat1(Long custId, Integer custStat1) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_ID, custId);
		paramMap.put(ParamConstant.CUST_STAT1, custStat1);
		return customerMapper.selectCountByCustIdStat1(paramMap);
	}
}