package com.baidu.rigel.unique.dao;

import java.util.Date;
import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.SeasonCustList;

public interface SeasonCustListDao extends SqlMapDao<SeasonCustList, Long> {

	public List<SeasonCustList> selectSeasonCustListByUrlPosIdList(String url, List<Long> posIdList);

	public List<SeasonCustList> selectSeasonCustListByDomainPosIdList(String domain, List<Long> posIdList);

	public List<SeasonCustList> selectSeasonCustListByPhoneNumPosIdList(String phoneNum, List<Long> posIdList);

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList);

	public List<SeasonCustList> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize);

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList);

	public void deleteSeasonCustList(Long id);
}