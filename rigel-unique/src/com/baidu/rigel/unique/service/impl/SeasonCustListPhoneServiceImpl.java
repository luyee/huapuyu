package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.dao.SeasonCustListPhoneDao;
import com.baidu.rigel.unique.service.SeasonCustListPhoneService;
import com.baidu.rigel.unique.utils.Utils;

@Service("seasonCustListPhoneService")
public class SeasonCustListPhoneServiceImpl extends GenericSqlMapServiceImpl<SeasonCustListPhone, Long> implements SeasonCustListPhoneService {

	@Autowired
	private SeasonCustListPhoneDao seasonCustListPhoneDao;

	@Override
	public SqlMapDao<SeasonCustListPhone, Long> getDao() {
		return seasonCustListPhoneDao;
	}

	public List<SeasonCustListPhone> selectSeasonCustListPhoneBySeasonCustListId(Long seasonCustListId) {
		if (Utils.isNull(seasonCustListId))
			return new ArrayList<SeasonCustListPhone>(0);
		return seasonCustListPhoneDao.selectSeasonCustListPhoneBySeasonCustListId(seasonCustListId);
	}

	public void deleteSeasonCustListPhone(Long id) {
		if (Utils.isNotNull(id))
			seasonCustListPhoneDao.deleteSeasonCustListPhone(id);
	}
}
