package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.UrlWhitelist;

public interface UrlWhitelistService extends GenericService<UrlWhitelist, Long> {

	public boolean isDomainAndPosIdsExist(String domain, Long[] posIds);

	public List<Map<String, Object>> pageList(Long posId, String domain, Short cType, Long updateId, int curPage, int pageSize);

	public Long pageCount(Long posId, String domain, Short cType, Long updateId);

	public List<Long> selectDisUpdateIdByPosId(Long posId);

	public boolean isDomainAndPosIdExist(String domain, Long posId);

	public void addUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid);

	public void updateUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid);

	public void deleteUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid);
}
