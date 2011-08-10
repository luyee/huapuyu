package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.UrlWhitelist;

@SqlMapper
public interface UrlWhitelistMapper extends DaoMapper<UrlWhitelist, Long> {
	public Long selectCountByDomainPosIdList(Map<String, Object> paramMap);

	public List<Map<String, Object>> pageList(Map<String, Object> paramMap);

	public Long pageCount(Map<String, Object> paramMap);

	public List<Long> selectDisUpdateIdByPosId(Long posId);

	public Long selectCountByDomainPosId(Map<String, Object> paramMap);
}