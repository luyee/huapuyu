package com.baidu.rigel.unique.service;

import java.util.Date;
import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.SeasonCustList;

public interface SeasonCustListService extends GenericService<SeasonCustList, Long> {

	public List<SeasonCustList> equalUrl(String url, List<Long> posIdList);

	public List<SeasonCustList> querySeasonCustDataByCoreWord(String custName, List<Long> posIdList, int limit);

	public List<SeasonCustList> querySeasonCustDataByDomain(String domain, List<Long> posIdList);

	public List<SeasonCustList> querySeasonCustDataByPhone(String phone, List<Long> posIdList);

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList);

	public List<SeasonCustList> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize);

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList);

	public void deleteSeasonCustList(Long id);
}
