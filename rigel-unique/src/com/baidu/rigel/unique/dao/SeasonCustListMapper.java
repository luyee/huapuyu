package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.SeasonCustList;

@SqlMapper
public interface SeasonCustListMapper extends DaoMapper<SeasonCustList, Long> {

	public List<SeasonCustList> selectSeasonCustListByUrlPosIdList(Map<String, Object> paramMap);

	public List<SeasonCustList> selectSeasonCustListByDomainPosIdList(Map<String, Object> paramMap);

	public List<SeasonCustList> selectSeasonCustListByPhoneNumPosIdList(Map<String, Object> paramMap);

	public List<Long> selectDisCreateIdByPosIdList(List<Long> list);

	public List<SeasonCustList> pageList(Map<String, Object> paramMap);

	public Long pageCount(Map<String, Object> paramMap);

	public void deleteSeasonCustList(Long id);
}