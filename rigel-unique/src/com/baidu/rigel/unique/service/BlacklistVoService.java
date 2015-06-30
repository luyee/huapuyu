package com.baidu.rigel.unique.service;

import java.util.List;

import com.baidu.rigel.unique.vo.BlacklistVo;

public interface BlacklistVoService {
	public List<BlacklistVo> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize);

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList);

	public void delete(Long blacklistId);

	public BlacklistVo findBlacklistVo(Long blacklistId);
}
