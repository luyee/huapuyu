package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;

@SqlMapper
public interface SeasonCustListPhoneMapper extends DaoMapper<SeasonCustListPhone, Long> {
	public List<SeasonCustListPhone> selectSeasonCustListPhoneBySeasonCustListId(Long seasonCustListId);

	public void deleteSeasonCustListPhone(Long id);
}