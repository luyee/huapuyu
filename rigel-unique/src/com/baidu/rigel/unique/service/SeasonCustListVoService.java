package com.baidu.rigel.unique.service;

import java.util.Date;
import java.util.List;

import com.baidu.rigel.unique.vo.SeasonCustListVo;

public interface SeasonCustListVoService {
	public List<SeasonCustListVo> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize);

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList);

	public void delete(Long id);

	public SeasonCustListVo findSeasonCustListVo(Long id);
}
