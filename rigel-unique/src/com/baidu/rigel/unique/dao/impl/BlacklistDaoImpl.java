package com.baidu.rigel.unique.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.dao.BlacklistDao;
import com.baidu.rigel.unique.dao.BlacklistMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("blacklistDao")
public class BlacklistDaoImpl extends BaseSqlMapDao<Blacklist, Long> implements BlacklistDao {
	@Autowired
	private BlacklistMapper blacklistMapper;

	@Override
	public DaoMapper<Blacklist, Long> getDaoMapper() {
		return blacklistMapper;
	}

	public List<Long> selectBlacklistIdByCompanyName(String companyName) {
		return blacklistMapper.selectBlacklistIdByCompanyName(companyName);
	}

	public List<Map<String, Object>> selectBlacklistIdCompanyNameLikeByCompanyName(String companyName) {
		return blacklistMapper.selectBlacklistIdCompanyNameLikeByCompanyName(companyName);
	}

	public List<Long> selectBlacklistIdByUrl(String url) {
		return blacklistMapper.selectBlacklistIdByUrl(url);
	}

	public List<Long> selectDisCreatorIdList() {
		return blacklistMapper.selectDisCreatorIdList();
	}

	public List<Blacklist> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.COMPANY_NAME, companyName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.LIST, srcList);
		paramMap.put(ParamConstant.START, curPage * pageSize);
		paramMap.put(ParamConstant.COUNT, pageSize);
		return blacklistMapper.pageList(paramMap);
	}

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.COMPANY_NAME, companyName);
		paramMap.put(ParamConstant.URL, url);
		paramMap.put(ParamConstant.CREATE_ID, createId);
		paramMap.put(ParamConstant.LIST, srcList);
		return blacklistMapper.pageCount(paramMap);
	}

	public void deleteBlacklist(Long blacklistId) {
		blacklistMapper.deleteBlacklist(blacklistId);
	}

}