package com.baidu.rigel.unique.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;
import com.baidu.rigel.unique.dao.ShifenCustWhiteListDao;
import com.baidu.rigel.unique.dao.ShifenCustWhiteListMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("shifenCustWhiteListDao")
public class ShifenCustWhiteListDaoImpl extends BaseSqlMapDao<ShifenCustWhiteList, Long> implements ShifenCustWhiteListDao {
	@Autowired
	private ShifenCustWhiteListMapper shifenCustWhiteListMapper;

	@Override
	public DaoMapper<ShifenCustWhiteList, Long> getDaoMapper() {
		return shifenCustWhiteListMapper;
	}

	public ShifenCustWhiteList selectShifenCustWhiteListByUrl(String url) {
		return shifenCustWhiteListMapper.selectShifenCustWhiteListByUrl(url);
	}

	public List<Long> selectDisUserIdByPosIdList(List<Long> posIdList) {
		return shifenCustWhiteListMapper.selectDisUserIdByPosIdList(posIdList);
	}

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList) {
		return shifenCustWhiteListMapper.selectDisCreateIdByPosIdList(posIdList);
	}

	public Long pageCount(String custName, String url, Long userId, Long createId, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_NAME, custName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.USER_ID, userId);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.LIST, posIdList);
		return shifenCustWhiteListMapper.pageCount(paramMap);
	}

	public List<ShifenCustWhiteList> pageList(String custName, String url, Long userId, Long createId, List<Long> posIdList, int curPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_NAME, custName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.USER_ID, userId);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.LIST, posIdList);
		paramMap.put(ParamConstant.START, curPage * pageSize);
		paramMap.put(ParamConstant.COUNT, pageSize);
		return shifenCustWhiteListMapper.pageList(paramMap);
	}

	public void deleteShifenCustWhiteList(Long id) {
		shifenCustWhiteListMapper.deleteShifenCustWhiteList(id);
	}
}