package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.dao.BlacklistPhoneDao;
import com.baidu.rigel.unique.service.BlacklistPhoneService;
import com.baidu.rigel.unique.utils.Utils;

@Service("blacklistPhoneService")
public class BlacklistPhoneServiceImpl extends GenericSqlMapServiceImpl<BlacklistPhone, Long> implements BlacklistPhoneService {

	@Autowired
	private BlacklistPhoneDao blacklistPhoneDao;

	public List<Long> equalPhoneNum(String phoneNum) {
		if (Utils.isNull(phoneNum))
			return new ArrayList<Long>();
		return blacklistPhoneDao.selectIdByPhoneNum(phoneNum);
	}

	public BlacklistPhone findById(Long id) {
		if (Utils.isNull(id))
			return null;
		return blacklistPhoneDao.findById(id);
	}

	public List<BlacklistPhone> selectBlacklistPhoneByBlacklistId(Long blacklistId) {
		if (Utils.isNull(blacklistId))
			return new ArrayList<BlacklistPhone>(0);
		return blacklistPhoneDao.selectBlacklistPhoneByBlacklistId(blacklistId);
	}

	public void deleteBlacklistPhone(Long id) {
		if (Utils.isNotNull(id))
			blacklistPhoneDao.deleteBlacklistPhone(id);
	}

	@Override
	public SqlMapDao<BlacklistPhone, Long> getDao() {
		return blacklistPhoneDao;
	}
}
