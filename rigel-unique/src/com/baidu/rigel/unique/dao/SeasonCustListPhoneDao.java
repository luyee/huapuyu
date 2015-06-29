package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;

public interface SeasonCustListPhoneDao extends SqlMapDao<SeasonCustListPhone, Long> {
	public List<SeasonCustListPhone> selectSeasonCustListPhoneBySeasonCustListId(Long seasonCustListId);

	public void deleteSeasonCustListPhone(Long id);
}