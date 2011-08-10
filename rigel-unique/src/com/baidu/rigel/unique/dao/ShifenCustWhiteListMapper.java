package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;

@SqlMapper
public interface ShifenCustWhiteListMapper extends DaoMapper<ShifenCustWhiteList, Long> {
	public ShifenCustWhiteList selectShifenCustWhiteListByUrl(String url);

	public List<Long> selectDisUserIdByPosIdList(List<Long> list);

	public List<Long> selectDisCreateIdByPosIdList(List<Long> list);

	public List<ShifenCustWhiteList> pageList(Map<String, Object> paramMap);

	public Long pageCount(Map<String, Object> paramMap);

	public void deleteShifenCustWhiteList(Long id);
}