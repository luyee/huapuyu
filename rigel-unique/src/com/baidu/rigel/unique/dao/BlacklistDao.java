package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.Blacklist;

public interface BlacklistDao extends SqlMapDao<Blacklist, Long> {

	public List<Long> selectBlacklistIdByCompanyName(String companyName);

	public List<Map<String, Object>> selectBlacklistIdCompanyNameLikeByCompanyName(String companyName);

	public List<Long> selectBlacklistIdByUrl(String url);

	public List<Long> selectDisCreatorIdList();

	public List<Blacklist> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize);

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList);

	public void deleteBlacklist(Long blacklistId);
}