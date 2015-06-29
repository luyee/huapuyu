package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.UrlWhitelist;

public interface UrlWhitelistDao extends SqlMapDao<UrlWhitelist, Long> {
	public Long selectCountByDomainPosIdList(String domain, List<Long> posIdList);

	public List<Map<String, Object>> pageList(Long posId, String domain, Short cType, Long updateId, int curPage, int pageSize);

	public Long pageCount(Long posId, String domain, Short cType, Long updateId);

	public List<Long> selectDisUpdateIdByPosId(Long posId);

	public Long selectCountByDomainPosId(String domain, Long posId);
}