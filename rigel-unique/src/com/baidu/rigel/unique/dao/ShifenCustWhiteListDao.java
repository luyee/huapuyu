package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;

public interface ShifenCustWhiteListDao extends SqlMapDao<ShifenCustWhiteList, Long> {
	public ShifenCustWhiteList selectShifenCustWhiteListByUrl(String url);

	public List<Long> selectDisUserIdByPosIdList(List<Long> posIdList);

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList);

	public List<ShifenCustWhiteList> pageList(String custName, String url, Long userId, Long createId, List<Long> posIdList, int curPage, int pageSize);

	public Long pageCount(String custName, String url, Long userId, Long createId, List<Long> posIdList);

	public void deleteShifenCustWhiteList(Long id);
}