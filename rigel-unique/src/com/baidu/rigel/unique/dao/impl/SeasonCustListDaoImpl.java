package com.baidu.rigel.unique.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.dao.SeasonCustListDao;
import com.baidu.rigel.unique.dao.SeasonCustListMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("seasonCustListDao")
public class SeasonCustListDaoImpl extends BaseSqlMapDao<SeasonCustList, Long> implements SeasonCustListDao {
	@Autowired
	private SeasonCustListMapper seasonCustListMapper;

	@Override
	public DaoMapper<SeasonCustList, Long> getDaoMapper() {
		return seasonCustListMapper;
	}

	public List<SeasonCustList> selectSeasonCustListByUrlPosIdList(String url, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.LIST, posIdList);
		return seasonCustListMapper.selectSeasonCustListByUrlPosIdList(paramMap);
	}

	public List<SeasonCustList> selectSeasonCustListByDomainPosIdList(String domain, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.DOMAIN, domain);
		paramMap.put(ParamConstant.LIST, posIdList);
		return seasonCustListMapper.selectSeasonCustListByDomainPosIdList(paramMap);
	}

	public List<SeasonCustList> selectSeasonCustListByPhoneNumPosIdList(String phoneNum, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.PHONE_NUM, phoneNum);
		paramMap.put(ParamConstant.LIST, posIdList);
		return seasonCustListMapper.selectSeasonCustListByPhoneNumPosIdList(paramMap);
	}

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList) {
		return seasonCustListMapper.selectDisCreateIdByPosIdList(posIdList);
	}

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_NAME, custName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.PHONE_NUM, phoneNum);
		paramMap.put(ParamConstant.USE_INVALIDATE, useInvalidate);
		paramMap.put(ParamConstant.BEGIN_INVALIDATE, beginInvalidate);
		paramMap.put(ParamConstant.END_INVALIDATE, endInvalidate);
		paramMap.put(ParamConstant.LIST, posIdList);
		return seasonCustListMapper.pageCount(paramMap);
	}

	public List<SeasonCustList> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CUST_NAME, custName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.PHONE_NUM, phoneNum);
		paramMap.put(ParamConstant.USE_INVALIDATE, useInvalidate);
		paramMap.put(ParamConstant.BEGIN_INVALIDATE, beginInvalidate);
		paramMap.put(ParamConstant.END_INVALIDATE, endInvalidate);
		paramMap.put(ParamConstant.LIST, posIdList);
		paramMap.put(ParamConstant.START, curPage * pageSize);
		paramMap.put(ParamConstant.COUNT, pageSize);
		return seasonCustListMapper.pageList(paramMap);
	}

	public void deleteSeasonCustList(Long id) {
		seasonCustListMapper.deleteSeasonCustList(id);
	}
}