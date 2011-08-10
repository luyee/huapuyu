package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.Blacklist;

@SqlMapper
public interface BlacklistMapper extends DaoMapper<Blacklist, Long> {
	public List<Long> selectBlacklistIdByCompanyName(String companyName);

	public List<Map<String, Object>> selectBlacklistIdCompanyNameLikeByCompanyName(String companyName);

	public List<Long> selectBlacklistIdByUrl(String url);

	public List<Long> selectDisCreatorIdList();

	public List<Blacklist> pageList(Map<String, Object> paramMap);

	public Long pageCount(Map<String, Object> paramMap);

	public void deleteBlacklist(Long blacklistId);
}