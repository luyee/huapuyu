package com.baidu.rigel.unique.service.xuanyuan.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.dao.xuanyuan.CustomerDao;
import com.baidu.rigel.unique.service.xuanyuan.CustomerService;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.CustType;
import com.baidu.rigel.unique.utils.FieldConstant;
import com.baidu.rigel.unique.utils.ReadConfig;
import com.baidu.rigel.unique.utils.Utils;

@Service("customerService")
public class CustomerServiceImpl extends GenericSqlMapServiceImpl<Customer, Long> implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ReadConfig readConfig;

	public List<Long> selectCustIdByCustFullName(String custFullName) {
		if (Utils.isNull(custFullName))
			return new ArrayList<Long>(0);
		return customerDao.selectCustIdByCustFullName(custFullName);
	}

	public List<Long> selectCustIdByCustBranchNameOrCustName(String name, CustType custType) {
		if (Utils.isNull(name) || Utils.isNull(custType))
			return new ArrayList<Long>(0);
		switch (custType) {
		case SPECIAL_ENTERPRISE:
			return customerDao.selectCustIdByCustName(name, custType);
		case ADVERTISING_AGENCY:
			return customerDao.selectCustIdByCustBranchName(name, custType);
		default:
			throw new IllegalArgumentException(String.format(readConfig.getErrorParamMsg(), "custType"));
		}
	}

	public Map<Long, Long> selectCustIdPosIdByCustIds(Long... custIds) {
		Map<Long, Long> resultMap = new HashMap<Long, Long>();

		if (ArrayUtils.isEmpty(custIds))
			return resultMap;

		List<Map<String, Long>> list = customerDao.selectCustIdPoseIdByCustIdList(Arrays.asList(custIds));

		if (CollectionUtils.isNotEmpty(list))
			for (Map<String, Long> map : list)
				resultMap.put(map.get(FieldConstant.CUST_ID), map.get(FieldConstant.POSE_ID));

		return resultMap;
	}

	public Map<Long, String> selectCustIdFullNameByCustIds(Long... custIds) {
		Map<Long, String> resultMap = new HashMap<Long, String>();

		if (ArrayUtils.isEmpty(custIds))
			return resultMap;

		List<Map<String, Object>> list = customerDao.selectCustIdFullNameByCustIdList(Arrays.asList(custIds));

		if (CollectionUtils.isNotEmpty(list))
			for (Map<String, Object> map : list)
				resultMap.put((Long) map.get(FieldConstant.CUST_ID), (String) map.get(FieldConstant.CUST_FULL_NAME));

		return resultMap;
	}

	public boolean isCustIdsExist(List<Long> custIdList, Long posId, Long custId) {
		if (CollectionUtils.isEmpty(custIdList))
			return Boolean.FALSE;

		return Utils.isGreaterThanZero(customerDao.selectCountByCustIdListPoseIdCustId(custIdList, posId, custId));
	}

	public List<Map<String, Object>> selectCustomerFollowDistributeByCustIdList(List<Long> custIdList) {
		if (CollectionUtils.isEmpty(custIdList))
			return new ArrayList<Map<String, Object>>(0);
		return customerDao.selectCustomerFollowDistributeByCustIdList(custIdList);
	}

	public Map<String, Object> selectCustIdFullNamePoseIdInputTypeByCustId(Long custId) {
		if (Utils.isNullOrEqualToZero(custId))
			return new HashMap<String, Object>();
		return customerDao.selectCustIdFullNamePoseIdInputTypeByCustId(custId);
	}

	public List<Map<String, Object>> selectCustIdFullNameLikeByCustUrlName(String custUrlName, int count) {
		if (StringUtils.isBlank(custUrlName) || Utils.isEqualLessThanZero(count))
			return new ArrayList<Map<String, Object>>(0);
		return customerDao.selectCustIdFullNameLikeByCustUrlName(Utils.addRightWildcard(Utils.escapeWildcard(custUrlName)), count);
	}

	public List<Map<String, Object>> selectCustIdFullNameByCustUrlName(String custUrlName) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(0);
		if (StringUtils.isBlank(custUrlName))
			return resultList;

		resultList = customerDao.selectCustIdFullNameByCustUrlName(custUrlName);
		if (CollectionUtils.isNotEmpty(resultList))
			for (Iterator<Map<String, Object>> it = resultList.iterator(); it.hasNext();) {
				Map<String, Object> map = it.next();
				Long count = customerDao.selectCountByCustIdStat1((Long) map.get(FieldConstant.CUST_ID), Constant.SALE_SIGN);
				if (Utils.isGreaterThanZero(count))
					it.remove();
			}

		return resultList;
	}

	public List<Map<String, Object>> selectCustIdFullNameByDomain(String domain) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(0);
		if (StringUtils.isBlank(domain))
			return resultList;

		resultList = customerDao.selectCustIdFullNameByDomain(domain);
		if (CollectionUtils.isNotEmpty(resultList))
			for (Iterator<Map<String, Object>> it = resultList.iterator(); it.hasNext();) {
				Map<String, Object> map = it.next();
				Long count = customerDao.selectCountByCustIdStat1((Long) map.get(FieldConstant.CUST_ID), Constant.SALE_SIGN);
				if (Utils.isGreaterThanZero(count))
					it.remove();
			}

		return resultList;
	}

	public List<Map<String, Object>> selectCustIdFullNameLikeByDomain(String domain) {
		if (StringUtils.isBlank(domain))
			return new ArrayList<Map<String, Object>>(0);
		return customerDao.selectCustIdFullNameLikeByDomain(Utils.addRightWildcard(Utils.escapeWildcard(domain)));
	}

	@Override
	public SqlMapDao<Customer, Long> getDao() {
		return customerDao;
	}

	public void delete(Long custId) {
		if (Utils.isNull(custId))
			return;

		customerDao.delete(custId);
	}
}
