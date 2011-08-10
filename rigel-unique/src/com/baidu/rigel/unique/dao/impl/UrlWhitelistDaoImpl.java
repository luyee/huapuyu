package com.baidu.rigel.unique.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.UrlWhitelist;
import com.baidu.rigel.unique.dao.UrlWhitelistDao;
import com.baidu.rigel.unique.dao.UrlWhitelistMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("urlWhitelistDao")
public class UrlWhitelistDaoImpl extends BaseSqlMapDao<UrlWhitelist, Long> implements UrlWhitelistDao {
	@Autowired
	private UrlWhitelistMapper urlWhitelistMapper;

	@Override
	public DaoMapper<UrlWhitelist, Long> getDaoMapper() {
		return urlWhitelistMapper;
	}

	public Long selectCountByDomainPosIdList(String domain, List<Long> posIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.DOMAIN, domain);
		paramMap.put(ParamConstant.LIST, posIdList);
		return urlWhitelistMapper.selectCountByDomainPosIdList(paramMap);
	}

	public Long pageCount(Long posId, String domain, Short cType, Long updateId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.POS_ID, posId);
		paramMap.put(ParamConstant.DOMAIN, domain);
		paramMap.put(ParamConstant.C_TYPE, cType);
		paramMap.put(ParamConstant.UPDATE_ID, updateId);
		return urlWhitelistMapper.pageCount(paramMap);
	}

	public List<Map<String, Object>> pageList(Long posId, String domain, Short cType, Long updateId, int curPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.POS_ID, posId);
		paramMap.put(ParamConstant.DOMAIN, domain);
		paramMap.put(ParamConstant.C_TYPE, cType);
		paramMap.put(ParamConstant.UPDATE_ID, updateId);
		paramMap.put(ParamConstant.START, curPage * pageSize);
		paramMap.put(ParamConstant.COUNT, pageSize);
		return urlWhitelistMapper.pageList(paramMap);
	}

	public List<Long> selectDisUpdateIdByPosId(Long posId) {
		return urlWhitelistMapper.selectDisUpdateIdByPosId(posId);
	}

	public Long selectCountByDomainPosId(String domain, Long posId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.POS_ID, posId);
		paramMap.put(ParamConstant.DOMAIN, domain);
		return urlWhitelistMapper.selectCountByDomainPosId(paramMap);
	}
}