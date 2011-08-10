package com.baidu.rigel.unique.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.dao.SeasonCustListPhoneDao;
import com.baidu.rigel.unique.dao.SeasonCustListPhoneMapper;

@Service("seasonCustListPhoneDao")
public class SeasonCustListPhoneDaoImpl extends BaseSqlMapDao<SeasonCustListPhone, Long> implements SeasonCustListPhoneDao {
	@Autowired
	private SeasonCustListPhoneMapper seasonCustListPhoneMapper;

	@Override
	public DaoMapper<SeasonCustListPhone, Long> getDaoMapper() {
		return seasonCustListPhoneMapper;
	}

	public List<SeasonCustListPhone> selectSeasonCustListPhoneBySeasonCustListId(Long seasonCustListId) {
		return seasonCustListPhoneMapper.selectSeasonCustListPhoneBySeasonCustListId(seasonCustListId);
	}

	public void deleteSeasonCustListPhone(Long id) {
		seasonCustListPhoneMapper.deleteSeasonCustListPhone(id);
	}
}