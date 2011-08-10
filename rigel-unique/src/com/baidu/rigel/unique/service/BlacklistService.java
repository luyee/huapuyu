package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.Blacklist;

public interface BlacklistService extends GenericService<Blacklist, Long> {

	public List<Long> equalCompanyName(String companyName);

	public List<Map<String, Object>> containCompanyName(String companyName);

	public List<Long> equalUrl(String url);

	public List<Long> selectDisCreatorIdList();

	public List<Blacklist> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize);

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList);

	public void deleteBlacklist(Long blacklistId);
}
